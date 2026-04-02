package com.bsn.infra.cdk.stack;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerInsights;
import software.constructs.Construct;

public class ComputeStack extends Stack {

    private Cluster cluster;


    public ComputeStack(Construct scope, String id, StackProps props,
                        Vpc vpc ) {
        super(scope, id, props);
        init(vpc);
    }


    public Cluster getCluster() {
        return cluster;
    }

    private void init(Vpc vpc) {
        // Cluster
        cluster = Cluster.Builder.create(this, "bsn-cluster")
                .clusterName("bsn")
                .vpc(vpc)
                .containerInsightsV2(ContainerInsights.ENHANCED)
                .build();
    }

}
