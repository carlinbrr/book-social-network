package com.bsn.infrastructure;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.Protocol;
import software.amazon.awscdk.services.ecs.Volume;
import software.amazon.awscdk.services.efs.FileSystem;
import software.amazon.awscdk.services.efs.PerformanceMode;
import software.amazon.awscdk.services.efs.ThroughputMode;
import software.amazon.awscdk.services.elasticloadbalancingv2.*;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.elasticloadbalancingv2.IpAddressType;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.rds.*;
import software.amazon.awscdk.services.secretsmanager.SecretTargetAttachment;
import software.constructs.Construct;

import java.util.List;
import java.util.Map;

public class BackendStack extends Stack {

    private static final String API_DATABASE_NAME = "book_social_network";

    private static final String POSTGRES_DATABASE_NAME = "postgres";


    public BackendStack(Construct scope, String id, StackProps props, Vpc vpc, SecurityGroup albSG,
                        SecurityGroup ecsSG, SecurityGroup rdsSG, SecurityGroup efsSG) {
        super(scope, id, props);

        // ECS Cluster
        Cluster ecsCluster = Cluster.Builder.create(this, "bsn-cluster")
                .clusterName("bsn")
                .vpc(vpc)
                .containerInsightsV2(ContainerInsights.ENHANCED)
                .build();

        // PostgreSQL RDS
        DatabaseInstance rds = DatabaseInstance.Builder.create(this, "bsn-rds")
                .engine(DatabaseInstanceEngine.postgres(
                        PostgresInstanceEngineProps.builder()
                                .version(PostgresEngineVersion.VER_18)
                                .build()
                ))
                .credentials(Credentials.fromGeneratedSecret(System.getenv("ROOT_DB_USER"),
                        CredentialsBaseOptions.builder()
                                .secretName("bsn-rds")
                                .build()))
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE3, InstanceSize.MICRO))
                .allocatedStorage(30)
                .multiAz(false)
                .networkType(NetworkType.IPV4)
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder()
                        .subnetType(SubnetType.PRIVATE_WITH_EGRESS)
                        .build())
                .securityGroups(List.of(rdsSG))
                .caCertificate(CaCertificate.RDS_CA_RSA2048_G1)
                .publiclyAccessible(false)
                .port(5432)
                .databaseInsightsMode(DatabaseInsightsMode.STANDARD)
                .enablePerformanceInsights(true)
                .performanceInsightRetention(PerformanceInsightRetention.DEFAULT)
                .databaseName(POSTGRES_DATABASE_NAME)
                .backupRetention(Duration.days(1))
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        // Secrets Database API DDL, API DML amd Mail
        DatabaseSecret apiDdlSecret = DatabaseSecret.Builder.create(this, "bsn-api-ddl-secret")
                .username(System.getenv("API_DDL_USER"))
                .secretName("bsn-api-ddl")
                .dbname(API_DATABASE_NAME)
                .build();

        SecretTargetAttachment.Builder.create(this, "bsn-api-ddl-secret-attachment")
                .secret(apiDdlSecret)
                .target(rds)
                .build();

        DatabaseSecret apiDmlSecret = DatabaseSecret.Builder.create(this, "bsn-api-dml-secret")
                .username(System.getenv("API_DML_USER"))
                .secretName("bsn-api-dml")
                .dbname(API_DATABASE_NAME)
                .build();

        SecretTargetAttachment.Builder.create(this, "bsn-api-dml-secret-attachment")
                .secret(apiDmlSecret)
                .target(rds)
                .build();

        software.amazon.awscdk.services.secretsmanager.Secret mailSecret = software.amazon.awscdk.services.secretsmanager.Secret.Builder.create(this, "bsn-mail-secret")
                .secretName("bsn-mail")
                .secretObjectValue(Map.of(
                        "username", SecretValue.Builder.create(System.getenv("MAIL_USER")).build(),
                        "password", SecretValue.Builder.create(System.getenv("MAIL_PASSWORD")).build()
                        ))
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        // Migrator Task Definition
        FargateTaskDefinition migratorTask = FargateTaskDefinition.Builder.create(this, "bsn-migrator-task")
                .family("bsn-migrator")
                .cpu(1024)
                .memoryLimitMiB(3072)
                .build();

        // Migrator Container
        migratorTask.addContainer("bsn-migrator-container", ContainerDefinitionOptions.builder()
                .containerName("bsn-migrator")
                .essential(true)
                .image(ContainerImage.fromRegistry("carlinbrr/bsn-migrator:latest"))
                .environment(
                        Map.of(
                                "HOST", rds.getDbInstanceEndpointAddress()
                        )
                )
                .secrets(
                        Map.of(
                                "ROOT_DB_USER", Secret.fromSecretsManager(rds.getSecret(), "username"),
                                "ROOT_DB_PASSWORD", Secret.fromSecretsManager(rds.getSecret(), "password"),
                                "API_DML_USER", Secret.fromSecretsManager(apiDmlSecret, "username"),
                                "API_DML_PASSWORD", Secret.fromSecretsManager(apiDmlSecret, "password"),
                                "API_DDL_USER", Secret.fromSecretsManager(apiDdlSecret, "username"),
                                "API_DDL_PASSWORD", Secret.fromSecretsManager(apiDdlSecret, "password")
                        )
                )
                .logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
                        .streamPrefix("migrator")
                        .logRetention(RetentionDays.ONE_WEEK)
                        .build()))
                .build());

        // EFS
        FileSystem efs = FileSystem.Builder.create(this, "bsn-efs")
                .fileSystemName("bsn")
                .oneZone(false)
                .enableAutomaticBackups(true)
                .throughputMode(ThroughputMode.ELASTIC)
                .performanceMode(PerformanceMode.GENERAL_PURPOSE)
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder()
                        .subnetType(SubnetType.PRIVATE_WITH_EGRESS)
                        .build())
                .securityGroup(efsSG)
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        // API Volume
        Volume bsnVolume = Volume.builder()
                .name("bsn-api")
                .efsVolumeConfiguration(EfsVolumeConfiguration.builder()
                        .fileSystemId(efs.getFileSystemId())
                        .rootDirectory("/")
                        .build())
                .build();

        // API Task Definition
        FargateTaskDefinition apiTask = FargateTaskDefinition.Builder.create(this, "bsn-api-task")
                .family("bsn-api")
                .cpu(1024)
                .memoryLimitMiB(3072)
                .volumes(List.of(bsnVolume))
                .build();

        // API Container
        ContainerDefinition apiContainer = apiTask.addContainer("bsn-api-container", ContainerDefinitionOptions.builder()
                .containerName("bsn-api")
                .essential(true)
                .image(ContainerImage.fromRegistry("carlinbrr/bsn-api:latest"))
                .portMappings(List.of(
                        PortMapping.builder()
                                .containerPort(8080)
                                .hostPort(8080)
                                .protocol(Protocol.TCP)
                                .build()
                ))
                .environment(
                        Map.of(
                                "DB_URL", getDatabaseJDBCUrl(rds.getDbInstanceEndpointAddress(), API_DATABASE_NAME),
                                "MAIL_HOST", System.getenv("MAIL_HOST"),
                                "MAIL_PORT", System.getenv("MAIL_PORT"),
                                "JWT_ISSUER_URI", System.getenv("JWT_ISSUER_URI"),
                                "APP_FRONTEND_HOST", System.getenv("APP_FRONTEND_HOST"),
                                "JPA_DDL_AUTO", "validate"
                        )
                )
                .secrets(
                        Map.of(
                                "DB_USER", Secret.fromSecretsManager(apiDmlSecret, "username"),
                                "DB_PASSWORD", Secret.fromSecretsManager(apiDmlSecret, "password"),
                                "MAIL_USER", Secret.fromSecretsManager(mailSecret, "username"),
                                "MAIL_PASSWORD", Secret.fromSecretsManager(mailSecret, "password")
                        )
                )
                .logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                .streamPrefix("api")
                                .logRetention(RetentionDays.ONE_WEEK)
                        .build()))
                .build());

        apiContainer.addMountPoints(MountPoint.builder()
                .sourceVolume(bsnVolume.getName())
                .containerPath("/app/images")
                .readOnly(false)
                .build());

        // API ECS - No task needs to run initially
        FargateService apiService = FargateService.Builder.create(this, "bsn-api-ecs")
                .serviceName("bsn-api")
                .cluster(ecsCluster)
                .taskDefinition(apiTask)
                .taskDefinitionRevision(TaskDefinitionRevision.LATEST)
                .desiredCount(0)
                .availabilityZoneRebalancing(AvailabilityZoneRebalancing.ENABLED)
                .healthCheckGracePeriod(Duration.seconds(0))
                .deploymentStrategy(DeploymentStrategy.ROLLING)
                .minHealthyPercent(100)
                .maxHealthyPercent(200)
                .platformVersion(FargatePlatformVersion.LATEST)
                .enableExecuteCommand(false)
                .vpcSubnets(SubnetSelection.builder()
                        .subnetType(SubnetType.PRIVATE_WITH_EGRESS)
                        .build())
                .securityGroups(List.of(ecsSG))
                .assignPublicIp(false)
                .build();

        // ALB
        ApplicationLoadBalancer alb = ApplicationLoadBalancer.Builder.create(this, "bsn-alb")
                .loadBalancerName("bsn")
                .internetFacing(true)
                .ipAddressType(IpAddressType.IPV4)
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder()
                        .subnetType(SubnetType.PUBLIC)
                        .build())
                .securityGroup(albSG)
                .build();

        ApplicationTargetGroup apiTG = ApplicationTargetGroup.Builder.create(this, "bsn-api-tg")
                .targetGroupName("bsn-api")
                .targetType(TargetType.IP)
                .protocol(ApplicationProtocol.HTTP)
                .port(8080)
                .ipAddressType(TargetGroupIpAddressType.IPV4)
                .vpc(vpc)
                .protocolVersion(ApplicationProtocolVersion.HTTP1)
                .healthCheck(HealthCheck.builder()
                        .protocol(software.amazon.awscdk.services.elasticloadbalancingv2.Protocol.HTTP)
                        .port("8080")
                        .path("/api/v1/ping")
                        .healthyThresholdCount(3)
                        .unhealthyThresholdCount(2)
                        .timeout(Duration.seconds(25))
                        .interval(Duration.seconds(30))
                        .healthyHttpCodes("200")
                        .build())
                .build();

        apiService.attachToApplicationTargetGroup(apiTG);

        alb.addListener("bsn-api-http-listener",
                ApplicationListenerProps.builder()
                        .loadBalancer(alb)
                        .protocol(ApplicationProtocol.HTTP)
                        .port(80)
                        .defaultAction(ListenerAction.forward(List.of(apiTG)))
                        .build());
    }

    private String getDatabaseJDBCUrl(String databaseEndpointAddress, String databaseName) {
        return "jdbc:postgresql://" + databaseEndpointAddress + ":5432/" + databaseName;
    }

}
