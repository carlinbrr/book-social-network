package com.bsn.infra.cdk.stack;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.efs.FileSystem;
import software.amazon.awscdk.services.elasticloadbalancingv2.*;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.rds.DatabaseInstance;
import software.amazon.awscdk.services.secretsmanager.ISecret;
import software.constructs.Construct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bsn.infra.cdk.config.EnvironmentConfig.*;

public class ServicesStack extends Stack {

    private static final String API_SUBDOMAIN = "api";

    private static final String KEYCLOAK_SUBDOMAIN = "auth";

    private static final String API_DATABASE_NAME = "book_social_network";

    private static final String KEYCLOAK_DATABASE_NAME = "keycloak";

    private ApplicationLoadBalancer alb;


    public ServicesStack(Construct scope, String id, StackProps props,
                        Vpc vpc, SecurityGroup ecsSg, SecurityGroup albSg, FileSystem efs,
                         DatabaseInstance rds, ISecret apiDmlSecret, ISecret keycloakDbSecret) {
        super(scope, id, props);
        init(vpc, ecsSg, albSg, efs, rds, apiDmlSecret, keycloakDbSecret);
    }


    public ApplicationLoadBalancer getAlb() {
        return alb;
    }

    private void init(Vpc vpc, SecurityGroup ecsSg, SecurityGroup albSg, FileSystem efs, DatabaseInstance rds,
                      ISecret apiDmlSecret, ISecret keycloakDbSecret) {
        // ALB Certificate
        IListenerCertificate albCertificate = ListenerCertificate.fromArn(ALB_CERTIFICATE_ARN);

        // ALB
        alb = ApplicationLoadBalancer.Builder.create(this, "bsn-alb")
                .loadBalancerName("bsn")
                .internetFacing(true)
                .ipAddressType(IpAddressType.IPV4)
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder()
                        .subnetType(SubnetType.PUBLIC)
                        .build())
                .securityGroup(albSg)
                .build();

        // HTTP Listener
        alb.addListener("bsn-alb-http-listener",
                ApplicationListenerProps.builder()
                        .loadBalancer(alb)
                        .protocol(ApplicationProtocol.HTTP)
                        .port(80)
                        .defaultAction(
                                ListenerAction.redirect(
                                        RedirectOptions.builder()
                                                .protocol("HTTPS")
                                                .port("443")
                                                .build()
                                ))
                        .build());

        // HTTPS Listener
        ApplicationListener albHttpsListener = alb.addListener("bsn-alb-https-listener",
                ApplicationListenerProps.builder()
                        .loadBalancer(alb)
                        .protocol(ApplicationProtocol.HTTPS)
                        .port(443)
                        .certificates(List.of(albCertificate))
                        .defaultAction(
                                ListenerAction.fixedResponse(404,
                                        FixedResponseOptions.builder()
                                                .contentType("text/plain")
                                                .messageBody("Sorry, the page couldn't be found :(")
                                                .build())
                        )
                        .build());

        // API Target Group
        ApplicationTargetGroup apiTg = ApplicationTargetGroup.Builder.create(this, "bsn-api-tg")
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

        // API Rule
        albHttpsListener.addTargetGroups("bsn-alb-api-rule", AddApplicationTargetGroupsProps.builder()
                .priority(1)
                .conditions(List.of(ListenerCondition.hostHeaders(List.of(API_SUBDOMAIN + "." + BSN_DOMAIN))))
                .targetGroups(List.of(apiTg))
                .build());

        // Keycloak Target Group
        ApplicationTargetGroup keycloakTg = ApplicationTargetGroup.Builder.create(this, "bsn-keycloak-tg")
                .targetGroupName("bsn-keycloak")
                .targetType(TargetType.IP)
                .protocol(ApplicationProtocol.HTTP)
                .port(8080)
                .ipAddressType(TargetGroupIpAddressType.IPV4)
                .vpc(vpc)
                .protocolVersion(ApplicationProtocolVersion.HTTP1)
                .healthCheck(HealthCheck.builder()
                        .protocol(software.amazon.awscdk.services.elasticloadbalancingv2.Protocol.HTTP)
                        .port("9000")
                        .path("/health/live")
                        .healthyThresholdCount(3)
                        .unhealthyThresholdCount(3)
                        .timeout(Duration.seconds(55))
                        .interval(Duration.seconds(60))
                        .healthyHttpCodes("200")
                        .build())
                .build();

        // Keycloak Rule
        albHttpsListener.addTargetGroups("bsn-alb-keycloak-rule", AddApplicationTargetGroupsProps.builder()
                .priority(2)
                .conditions(List.of(ListenerCondition.hostHeaders(List.of(KEYCLOAK_SUBDOMAIN + "." + BSN_DOMAIN))))
                .targetGroups(List.of(keycloakTg))
                .build());

        // ECS Cluster
        Cluster ecsCluster = Cluster.Builder.create(this, "bsn-cluster")
                .clusterName("bsn")
                .vpc(vpc)
                .containerInsightsV2(ContainerInsights.ENHANCED)
                .build();

        // Mail and Keycloak secret
        ISecret mailSecret = software.amazon.awscdk.services.secretsmanager.Secret.Builder.create(this, "bsn-mail-secret")
                .secretName("bsn-mail")
                .secretObjectValue(Map.of(
                        "username", SecretValue.Builder.create(MAIL_USER).build(),
                        "password", SecretValue.Builder.create(MAIL_PASSWORD).build()
                ))
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        ISecret keycloakSecret = software.amazon.awscdk.services.secretsmanager.Secret.Builder.create(this, "bsn-keycloak-secret")
                .secretName("bsn-keycloak")
                .secretObjectValue(Map.of(
                        "username", SecretValue.Builder.create(KEYCLOAK_ADMIN_USER).build(),
                        "password", SecretValue.Builder.create(KEYCLOAK_ADMIN_PASSWORD).build()
                ))
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
                                .protocol(software.amazon.awscdk.services.ecs.Protocol.TCP)
                                .build()
                ))
                .environment(
                        Map.of(
                                "DB_URL", getDatabaseJDBCUrl(rds.getDbInstanceEndpointAddress(), API_DATABASE_NAME),
                                "MAIL_HOST", MAIL_HOST,
                                "MAIL_PORT", MAIL_PORT,
                                "JWT_ISSUER_URI", JWT_ISSUER_URI,
                                "FRONTEND_URL", FRONTEND_URL,
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
                .securityGroups(List.of(ecsSg))
                .assignPublicIp(false)
                .build();

        apiService.attachToApplicationTargetGroup(apiTg);

        // Keycloak Task Definition
        FargateTaskDefinition keycloakTask = FargateTaskDefinition.Builder.create(this, "bsn-keycloak-task")
                .family("bsn-keycloak")
                .cpu(1024)
                .memoryLimitMiB(3072)
                .build();

        Map<String, String> keycloakEnvironment = new HashMap<>();
        keycloakEnvironment.put("KC_DB", "postgres");
        keycloakEnvironment.put("KC_DB_URL", getDatabaseJDBCUrl(rds.getDbInstanceEndpointAddress(), KEYCLOAK_DATABASE_NAME));
        keycloakEnvironment.put("KC_HOSTNAME", KEYCLOAK_HOST);
        keycloakEnvironment.put("KC_HTTP_ENABLED", "true");
        keycloakEnvironment.put("KC_PROXY_HEADERS", "xforwarded");
        keycloakEnvironment.put("KC_HEALTH_ENABLED", "true");
        keycloakEnvironment.put("BSN_USERS_API_URL", BSN_USERS_API_URL);
        keycloakEnvironment.put("FRONTEND_URL", FRONTEND_URL);
        keycloakEnvironment.put("MAIL_HOST", MAIL_HOST);
        keycloakEnvironment.put("MAIL_PORT", MAIL_PORT);
        keycloakEnvironment.put("MAIL_SSL", "true");
        keycloakEnvironment.put("MAIL_START_TLS", "true");
        keycloakEnvironment.put("MAIL_AUTH", "true");

        // Keycloak Container
        keycloakTask.addContainer("bsn-keycloak-container", ContainerDefinitionOptions.builder()
                .containerName("bsn-keycloak")
                .essential(true)
                .image(ContainerImage.fromRegistry("carlinbrr/bsn-keycloak:latest"))
                .portMappings(List.of(
                        PortMapping.builder()
                                .containerPort(8080)
                                .hostPort(8080)
                                .protocol(software.amazon.awscdk.services.ecs.Protocol.TCP)
                                .build(),
                        PortMapping.builder()
                                .containerPort(9000)
                                .hostPort(9000)
                                .protocol(software.amazon.awscdk.services.ecs.Protocol.TCP)
                                .build()
                ))
                .environment(keycloakEnvironment)
                .secrets(
                        Map.of(
                                "KC_BOOTSTRAP_ADMIN_USERNAME", Secret.fromSecretsManager(keycloakSecret, "username"),
                                "KC_BOOTSTRAP_ADMIN_PASSWORD", Secret.fromSecretsManager(keycloakSecret, "password"),
                                "KC_DB_USERNAME", Secret.fromSecretsManager(keycloakDbSecret, "username"),
                                "KC_DB_PASSWORD", Secret.fromSecretsManager(keycloakDbSecret, "password"),
                                "MAIL_USER", Secret.fromSecretsManager(mailSecret, "username"),
                                "MAIL_PASSWORD", Secret.fromSecretsManager(mailSecret, "password")
                        )
                )
                .logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
                        .streamPrefix("keycloak")
                        .logRetention(RetentionDays.ONE_WEEK)
                        .build()))
                .command(List.of("start", "--import-realm"))
                .build());

        // Keycloak ECS - No task needs to run initially
        FargateService keycloakService = FargateService.Builder.create(this, "bsn-keycloak-ecs")
                .serviceName("bsn-keycloak")
                .cluster(ecsCluster)
                .taskDefinition(keycloakTask)
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
                .securityGroups(List.of(ecsSg))
                .assignPublicIp(false)
                .build();

        keycloakService.attachToApplicationTargetGroup(keycloakTg);
    }

    private String getDatabaseJDBCUrl(String databaseEndpointAddress, String databaseName) {
        return "jdbc:postgresql://" + databaseEndpointAddress + ":5432/" + databaseName;
    }

}
