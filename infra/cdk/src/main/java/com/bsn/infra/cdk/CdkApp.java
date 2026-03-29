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

        NetworkStack networkStack = new NetworkStack(app, "NetworkStack", props);

        StorageStack storageStack = new StorageStack(app, "StorageStack", props,
                networkStack.getVpc(), networkStack.getRdsSg(), networkStack.getEfsSg());

        new MigrationStack(app, "MigrationStack", props,
                storageStack.getRds(), storageStack.getApiDdlSecret(), storageStack.getApiDmlSecret(),
                storageStack.getKeycloakDbSecret());

        ServicesStack servicesStack = new ServicesStack(app, "ServicesStack", props,
                networkStack.getVpc(), networkStack.getEcsSg(), networkStack.getAlbSg(), storageStack.getEfs(),
                storageStack.getRds(), storageStack.getApiDmlSecret(), storageStack.getKeycloakDbSecret());

        FrontendStack frontendStack = new FrontendStack(app, "FrontendStack", props);

        new DnsStack(app, "DnsStack", props,
                servicesStack.getAlb(), frontendStack.getMainCfDistribution(), frontendStack.getRedirectCfDistribution());

        app.synth();
        System.out.println("Infrastructure created!");
    }

}
