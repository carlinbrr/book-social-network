package com.bsn.infrastructure;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.*;
import software.constructs.Construct;

import java.util.List;

public class NetworkStack extends Stack {

    private final Vpc vpc;

    private final SecurityGroup albSG;

    private final SecurityGroup ecsSG;

    private final SecurityGroup rdsSG;

    private final SecurityGroup efsSG;


    public NetworkStack(Construct scope, String id, StackProps props) {
        super(scope, id, props);

        // Create VPC and subnets
        vpc = Vpc.Builder
                .create(this, "bsn-vpc")
                .maxAzs(2)
                .natGateways(1)
                .subnetConfiguration(List.of(
                        SubnetConfiguration.builder()
                                .name("bsn-public-subnet")
                                .subnetType(SubnetType.PUBLIC)
                                .cidrMask(24)
                                .build(),
                        SubnetConfiguration.builder()
                                .name("bsn-private-subnet")
                                .subnetType(SubnetType.PRIVATE_WITH_EGRESS)
                                .cidrMask(24)
                                .build()
                ))
                .build();

        // Create Security Groups and inbound/outbound rules for ALB, ECS, RDS AND EFS
        albSG = SecurityGroup.Builder.create(this, "bsn-alb-sg")
                .vpc(vpc)
                .allowAllOutbound(false)
                .description("Security group for ALB")
                .build();
        albSG.addIngressRule(Peer.anyIpv4(), Port.tcp(443), "Allow HTTPS from everywhere");
        albSG.addIngressRule(Peer.anyIpv4(), Port.tcp(80), "Allow HTTP from everywhere");

        ecsSG = SecurityGroup.Builder.create(this, "bsn-ecs-sg")
                .vpc(vpc)
                .allowAllOutbound(true)
                .description("Security group for ECS")
                .build();
        ecsSG.addIngressRule(albSG, Port.tcp(8080), "Allow TCP 8080 from ALB");
        ecsSG.addIngressRule(albSG, Port.tcp(9000), "Allow TCP 9000 from ALB");

        albSG.addEgressRule(ecsSG,  Port.tcp(8080), "Allow TCP 8080 to ECS");
        albSG.addEgressRule(ecsSG,  Port.tcp(9000), "Allow TCP 9000 to ECS");

        rdsSG = SecurityGroup.Builder.create(this, "bsn-rds-sg")
                .vpc(vpc)
                .allowAllOutbound(true)
                .description("Security group for RDS")
                .build();
        rdsSG.addIngressRule(ecsSG, Port.tcp(5432), "Allow TCP 5432 from ECS");

        efsSG = SecurityGroup.Builder.create(this, "bsn-efs-sg")
                .vpc(vpc)
                .allowAllOutbound(true)
                .description("Security group for EFS")
                .build();
        efsSG.addIngressRule(ecsSG, Port.tcp(2049), "Allow TCP 2049 from ECS");
    }

}
