package com.bsn.infra.cdk;

import com.bsn.infra.cdk.stack.*;
import software.amazon.awscdk.App;
import software.amazon.awscdk.AppProps;
import software.amazon.awscdk.BootstraplessSynthesizer;
import software.amazon.awscdk.StackProps;

public class CdkApp {

    public static void main(String[] args) {
        System.out.println("Initializing Infrastructure...");

        App app = new App(AppProps.builder().outdir("./cdk.out").build());

        // Add BootstraplessSynthesizer since we don't need to upload any assets
        // Only template creation is needed
        StackProps props = StackProps.builder()
                .synthesizer(new BootstraplessSynthesizer())
                .build();

        NetworkStack networkStack = new NetworkStack(app, "Network", props);

        DatabaseStack databaseStack = new DatabaseStack(app, "Database", props,
                networkStack.getVpc(), networkStack.getRdsSg());

        FileSystemStack fileSystemStack = new FileSystemStack(app, "FileSystem", props,
                networkStack.getVpc(), networkStack.getEfsSg());

        ComputeStack computeStack = new ComputeStack(app, "Compute", props,
                networkStack.getVpc());

        new MigrationStack(app, "Migration", props,
                databaseStack.getRds(), databaseStack.getApiDdlSecret(), databaseStack.getApiDmlSecret(),
                databaseStack.getKeycloakDbSecret());

        ServicesStack servicesStack = new ServicesStack(app, "Services", props,
                networkStack.getVpc(), networkStack.getEcsSg(), networkStack.getAlbSg(), fileSystemStack.getEfs(),
                databaseStack.getRds(), computeStack.getCluster(), databaseStack.getApiDmlSecret(),
                databaseStack.getKeycloakDbSecret());

        FrontendStack frontendStack = new FrontendStack(app, "Frontend", props);

        new DNSStack(app, "DNS", props,
                servicesStack.getAlb(), frontendStack.getMainCfDistribution(), frontendStack.getRedirectCfDistribution());

        app.synth();
        System.out.println("Infrastructure created!");
    }

}
