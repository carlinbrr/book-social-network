package com.bsn.infrastructure;

import software.amazon.awscdk.App;
import software.amazon.awscdk.AppProps;
import software.amazon.awscdk.StackProps;

public class InfrastructureApp {

    public static void main(String[] args) {
        App app = new App(AppProps.builder().outdir("./cdk.out").build());

        NetworkStack networkStack = new NetworkStack(app, "NetworkStack", StackProps.builder().build());

        app.synth();
        System.out.println("Stack created!");
    }

}
