package com.bsn.infra.cdk.stack;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.efs.FileSystem;
import software.amazon.awscdk.services.efs.PerformanceMode;
import software.amazon.awscdk.services.efs.ThroughputMode;
import software.amazon.awscdk.services.rds.*;
import software.amazon.awscdk.services.secretsmanager.ISecret;
import software.constructs.Construct;

import java.util.List;

public class StorageStack extends Stack {

    public static final String API_DATABASE_NAME = "book_social_network";

    public static final String KEYCLOAK_DATABASE_NAME = "keycloak";

    public static final String POSTGRES_DATABASE_NAME = "postgres";

    private DatabaseInstance rds;

    private ISecret apiDdlSecret;

    private ISecret apiDmlSecret;

    private ISecret keycloakDbSecret;

    private FileSystem efs;


    public StorageStack(Construct scope, String id, StackProps props,
                        Vpc vpc, SecurityGroup rdsSg, SecurityGroup efsSg) {
        super(scope, id, props);
        init(vpc, rdsSg, efsSg);
    }


    public DatabaseInstance getRds() {
        return rds;
    }

    public ISecret getApiDdlSecret() {
        return apiDdlSecret;
    }

    public ISecret getApiDmlSecret() {
        return apiDmlSecret;
    }

    public ISecret getKeycloakDbSecret() {
        return keycloakDbSecret;
    }

    public FileSystem getEfs() {
        return efs;
    }

    private void init(Vpc vpc, SecurityGroup rdsSg, SecurityGroup efsSg) {
        // RDS
        rds = DatabaseInstance.Builder.create(this, "bsn-rds")
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
                .securityGroups(List.of(rdsSg))
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

        // Database Secrets
        apiDdlSecret = DatabaseSecret.Builder.create(this, "bsn-api-ddl-secret")
                .username(System.getenv("API_DDL_USER"))
                .secretName("bsn-api-ddl")
                .dbname(API_DATABASE_NAME)
                .build();

        apiDmlSecret = DatabaseSecret.Builder.create(this, "bsn-api-dml-secret")
                .username(System.getenv("API_DML_USER"))
                .secretName("bsn-api-dml")
                .dbname(API_DATABASE_NAME)
                .build();

        keycloakDbSecret = DatabaseSecret.Builder.create(this, "bsn-keycloak-db-secret")
                .username(System.getenv("KEYCLOAK_DB_USER"))
                .secretName("bsn-keycloak-db")
                .dbname(KEYCLOAK_DATABASE_NAME)
                .build();

        // EFS
        efs = FileSystem.Builder.create(this, "bsn-efs")
                .fileSystemName("bsn")
                .oneZone(false)
                .enableAutomaticBackups(true)
                .throughputMode(ThroughputMode.ELASTIC)
                .performanceMode(PerformanceMode.GENERAL_PURPOSE)
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder()
                        .subnetType(SubnetType.PRIVATE_WITH_EGRESS)
                        .build())
                .securityGroup(efsSg)
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();
    }

}
