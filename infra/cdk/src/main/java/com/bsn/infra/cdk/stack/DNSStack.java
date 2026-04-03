package com.bsn.infra.cdk.stack;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.cloudfront.Distribution;
import software.amazon.awscdk.services.elasticloadbalancingv2.ApplicationLoadBalancer;
import software.amazon.awscdk.services.route53.*;
import software.amazon.awscdk.services.route53.targets.CloudFrontTarget;
import software.amazon.awscdk.services.route53.targets.LoadBalancerTarget;
import software.constructs.Construct;

import static com.bsn.infra.cdk.config.EnvironmentConfig.*;

public class DNSStack extends Stack {

    private static final String API_RECORD_NAME = "api";

    private static final String AUTH_RECORD_NAME = "auth";

    private static final String WEB_RECORD_NAME = "www";

    private static final String EMPTY_RECORD_NAME = "";


    public DNSStack(Construct scope, String id, StackProps props, ApplicationLoadBalancer alb,
                    Distribution mainCfDistribution, Distribution redirectCfDistribution) {
        super(scope, id, props);
        init(alb, mainCfDistribution, redirectCfDistribution);
    }


    private void init(ApplicationLoadBalancer alb, Distribution mainCfDistribution, Distribution redirectCfDistribution) {
        // Hosted zone
        IHostedZone hostedZone = HostedZone.fromHostedZoneAttributes(this, "bsn-hosted-zone",
                HostedZoneAttributes.builder()
                        .hostedZoneId(HOSTED_ZONE_ID)
                        .zoneName(BSN_DOMAIN)
                        .build());

        // API record
        new ARecord(this, "bsn-api-record", ARecordProps.builder()
                .zone(hostedZone)
                .recordName(API_RECORD_NAME)
                .target(RecordTarget.fromAlias(new LoadBalancerTarget(alb)))
                .build());

        // Keycloak Record
        new ARecord(this, "bsn-auth-record", ARecordProps.builder()
                .zone(hostedZone)
                .recordName(AUTH_RECORD_NAME)
                .target(RecordTarget.fromAlias(new LoadBalancerTarget(alb)))
                .build());

        // Main Web Record
        new ARecord(this, "bsn-main-web-record", ARecordProps.builder()
                .zone(hostedZone)
                .recordName(WEB_RECORD_NAME)
                .target(RecordTarget.fromAlias(new CloudFrontTarget(mainCfDistribution)))
                .build());

        // Redirect Web Record
        new ARecord(this, "bsn-redirect-web-record", ARecordProps.builder()
                .zone(hostedZone)
                .recordName(EMPTY_RECORD_NAME)
                .target(RecordTarget.fromAlias(new CloudFrontTarget(redirectCfDistribution)))
                .build());
    }

}
