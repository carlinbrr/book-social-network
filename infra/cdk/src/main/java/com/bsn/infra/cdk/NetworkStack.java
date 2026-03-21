package com.bsn.infra.cdk;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.*;
import software.constructs.Construct;

import java.util.List;

public class NetworkStack extends Stack {

    private Vpc vpc;

    private SecurityGroup albSG;

    private SecurityGroup ecsSG;

    private SecurityGroup rdsSG;

    private SecurityGroup efsSG;


    public NetworkStack(Construct scope, String id, StackProps props) {
        super(scope, id, props);
        init();
    }


    public Vpc getVpc() {
        return vpc;
    }

    public SecurityGroup getAlbSG() {
        return albSG;
    }

    public SecurityGroup getEcsSG() {
        return ecsSG;
    }

    public SecurityGroup getRdsSG() {
        return rdsSG;
    }

    public SecurityGroup getEfsSG() {
        return efsSG;
    }


    private void init() {
        // Create VPC and subnets
        vpc = Vpc.Builder
                .create(this, "bsn-vpc")
                .vpcName("bsn-vpc")
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
                .securityGroupName("bsn-alb-sg")
                .allowAllOutbound(false)
                .description("Security group for ALB")
                .build();
        albSG.addIngressRule(Peer.anyIpv4(), Port.tcp(443), "Allow HTTPS from everywhere");
        albSG.addIngressRule(Peer.anyIpv4(), Port.tcp(80), "Allow HTTP from everywhere");

        ecsSG = SecurityGroup.Builder.create(this, "bsn-ecs-sg")
                .vpc(vpc)
                .securityGroupName("bsn-ecs-sg")
                .allowAllOutbound(true)
                .description("Security group for ECS")
                .build();
        ecsSG.addIngressRule(albSG, Port.tcp(8080), "Allow TCP 8080 from ALB");
        ecsSG.addIngressRule(albSG, Port.tcp(9000), "Allow TCP 9000 from ALB");

        albSG.addEgressRule(ecsSG,  Port.tcp(8080), "Allow TCP 8080 to ECS");
        albSG.addEgressRule(ecsSG,  Port.tcp(9000), "Allow TCP 9000 to ECS");

        rdsSG = SecurityGroup.Builder.create(this, "bsn-rds-sg")
                .vpc(vpc)
                .securityGroupName("bsn-rds-sg")
                .allowAllOutbound(true)
                .description("Security group for RDS")
                .build();
        rdsSG.addIngressRule(ecsSG, Port.tcp(5432), "Allow TCP 5432 from ECS");

        efsSG = SecurityGroup.Builder.create(this, "bsn-efs-sg")
                .vpc(vpc)
                .securityGroupName("bsn-efs-sg")
                .allowAllOutbound(true)
                .description("Security group for EFS")
                .build();
        efsSG.addIngressRule(ecsSG, Port.tcp(2049), "Allow TCP 2049 from ECS");
    }

}
