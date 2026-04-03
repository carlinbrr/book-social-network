package com.bsn.infra.cdk.stack;

import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.efs.FileSystem;
import software.amazon.awscdk.services.efs.PerformanceMode;
import software.amazon.awscdk.services.efs.ThroughputMode;
import software.constructs.Construct;

public class FileSystemStack extends Stack {

    private FileSystem efs;


    public FileSystemStack(Construct scope, String id, StackProps props,
                        Vpc vpc, SecurityGroup efsSg) {
        super(scope, id, props);
        init(vpc, efsSg);
    }


    public FileSystem getEfs() {
        return efs;
    }

    private void init(Vpc vpc, SecurityGroup efsSg) {
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
