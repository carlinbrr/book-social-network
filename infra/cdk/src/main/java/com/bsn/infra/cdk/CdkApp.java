package com.bsn.infra.cdk;

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
        BackendStack backendStack = new BackendStack(app, "BackendStack", props,
                networkStack.getVpc(), networkStack.getAlbSG(), networkStack.getEcsSG(),
                networkStack.getRdsSG(), networkStack.getEfsSG());

        app.synth();
        System.out.println("Infrastructure created!");
    }

}
