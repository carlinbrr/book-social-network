package com.bsn.infra.cdk.stack;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.rds.DatabaseInstance;
import software.amazon.awscdk.services.secretsmanager.ISecret;
import software.constructs.Construct;

import java.util.Map;

import static com.bsn.infra.cdk.config.EnvironmentConfig.*;

public class MigrationStack extends Stack {

    public MigrationStack(Construct scope, String id, StackProps props,
                          DatabaseInstance rds, ISecret apiDdlSecret,
                          ISecret apiDmlSecret, ISecret keycloakDbSecret) {
        super(scope, id, props);
        init(rds, apiDdlSecret, apiDmlSecret, keycloakDbSecret);
    }


    private void init(DatabaseInstance rds, ISecret apiDdlSecret, ISecret apiDmlSecret, ISecret keycloakDbSecret) {
        // Migration Task Definition
        FargateTaskDefinition migrationTask = FargateTaskDefinition.Builder.create(this, "bsn-migration-task")
                .family("bsn-migration")
                .cpu(1024)
                .memoryLimitMiB(3072)
                .build();

        // Migration Container
        migrationTask.addContainer("bsn-migration-container", ContainerDefinitionOptions.builder()
                .containerName("bsn-migration")
                .essential(true)
                .image(ContainerImage.fromRegistry("carlinbrr/bsn-migration:" + TAG_VERSION))
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
                                "API_DDL_PASSWORD", Secret.fromSecretsManager(apiDdlSecret, "password"),
                                "KEYCLOAK_DB_USER", Secret.fromSecretsManager(keycloakDbSecret, "username"),
                                "KEYCLOAK_DB_PASSWORD", Secret.fromSecretsManager(keycloakDbSecret, "password")
                        )
                )
                .logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
                        .streamPrefix("migration")
                        .logRetention(RetentionDays.ONE_WEEK)
                        .build()))
                .build());
    }

}
