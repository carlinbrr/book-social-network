package com.bsn.infra.cdk.stack;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.elasticloadbalancingv2.ApplicationLoadBalancer;
import software.amazon.awscdk.services.route53.*;
import software.amazon.awscdk.services.route53.targets.LoadBalancerTarget;
import software.constructs.Construct;

import static com.bsn.infra.cdk.config.EnvironmentConfig.*;

public class DnsStack extends Stack {

    private static final String API_RECORD_NAME = "api";

    private static final String AUTH_RECORD_NAME = "auth";


    public DnsStack(Construct scope, String id, StackProps props, ApplicationLoadBalancer alb) {
        super(scope, id, props);
        init(alb);
    }


    private void init(ApplicationLoadBalancer alb) {
        // Hosted zone
        IHostedZone hostedZone = HostedZone.fromHostedZoneAttributes(this, "bsn-hosted-zone",
                HostedZoneAttributes.builder()
                        .hostedZoneId(HOSTED_ZONE_ID)
                        .zoneName(BSN_DOMAIN)
                        .build());

        // API record
        ARecord apiRecord = new ARecord(this, "bsn-api-record", ARecordProps.builder()
                .zone(hostedZone)
                .recordName(API_RECORD_NAME)
                .target(RecordTarget.fromAlias(new LoadBalancerTarget(alb)))
                .build());

        // Keycloak Record
        ARecord authRecord = new ARecord(this, "bsn-auth-record", ARecordProps.builder()
                .zone(hostedZone)
                .recordName(AUTH_RECORD_NAME)
                .target(RecordTarget.fromAlias(new LoadBalancerTarget(alb)))
                .build());
    }

}
