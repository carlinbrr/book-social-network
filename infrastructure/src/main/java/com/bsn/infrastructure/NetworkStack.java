package com.bsn.infrastructure;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.constructs.Construct;

public class NetworkStack extends Stack {

    public NetworkStack(Construct scope, String id, StackProps props) {
        super(scope, id, props);
        CfnOutput.Builder.create(this, "TestOutput").value("Hello World!").build();
    }

}
