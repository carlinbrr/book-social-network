package com.bsn.infra.cdk.stack;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.*;
import software.constructs.Construct;

import java.util.List;

public class NetworkStack extends Stack {

    private Vpc vpc;

    private SecurityGroup albSg;

    private SecurityGroup ecsSg;

    private SecurityGroup rdsSg;

    private SecurityGroup efsSg;


    public NetworkStack(Construct scope, String id, StackProps props) {
        super(scope, id, props);
        init();
    }


    public Vpc getVpc() {
        return vpc;
    }

    public SecurityGroup getAlbSg() {
        return albSg;
    }

    public SecurityGroup getEcsSg() {
        return ecsSg;
    }

    public SecurityGroup getRdsSg() {
        return rdsSg;
    }

    public SecurityGroup getEfsSg() {
        return efsSg;
    }


    private void init() {
        // VPC and subnets
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

        // Security Groups and inbound/outbound rules for ALB, ECS, RDS AND EFS
        albSg = SecurityGroup.Builder.create(this, "bsn-alb-sg")
                .vpc(vpc)
                .securityGroupName("bsn-alb-sg")
                .allowAllOutbound(false)
                .description("Security group for ALB")
                .build();
        albSg.addIngressRule(Peer.anyIpv4(), Port.tcp(443), "Allow HTTPS from everywhere");
        albSg.addIngressRule(Peer.anyIpv4(), Port.tcp(80), "Allow HTTP from everywhere");

        ecsSg = SecurityGroup.Builder.create(this, "bsn-ecs-sg")
                .vpc(vpc)
                .securityGroupName("bsn-ecs-sg")
                .allowAllOutbound(true)
                .description("Security group for ECS")
                .build();
        ecsSg.addIngressRule(albSg, Port.tcp(8080), "Allow TCP 8080 from ALB");
        ecsSg.addIngressRule(albSg, Port.tcp(9000), "Allow TCP 9000 from ALB");

        albSg.addEgressRule(ecsSg,  Port.tcp(8080), "Allow TCP 8080 to ECS");
        albSg.addEgressRule(ecsSg,  Port.tcp(9000), "Allow TCP 9000 to ECS");

        rdsSg = SecurityGroup.Builder.create(this, "bsn-rds-sg")
                .vpc(vpc)
                .securityGroupName("bsn-rds-sg")
                .allowAllOutbound(true)
                .description("Security group for RDS")
                .build();
        rdsSg.addIngressRule(ecsSg, Port.tcp(5432), "Allow TCP 5432 from ECS");

        efsSg = SecurityGroup.Builder.create(this, "bsn-efs-sg")
                .vpc(vpc)
                .securityGroupName("bsn-efs-sg")
                .allowAllOutbound(true)
                .description("Security group for EFS")
                .build();
        efsSg.addIngressRule(ecsSg, Port.tcp(2049), "Allow TCP 2049 from ECS");
    }

}
