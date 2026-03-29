package com.bsn.infra.cdk.stack;

import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.certificatemanager.Certificate;
import software.amazon.awscdk.services.certificatemanager.ICertificate;
import software.amazon.awscdk.services.cloudfront.*;
import software.amazon.awscdk.services.cloudfront.origins.S3StaticWebsiteOrigin;
import software.amazon.awscdk.services.s3.*;
import software.constructs.Construct;

import java.util.List;

import static com.bsn.infra.cdk.config.EnvironmentConfig.*;

public class FrontendStack extends Stack {

    private static final String WEB_SUBDOMAIN = "www";

    private Distribution mainCfDistribution;

    private Distribution redirectCfDistribution;


    public FrontendStack(Construct scope, String id, StackProps props) {
        super(scope, id, props);
        init();
    }


    public Distribution getMainCfDistribution() {
        return mainCfDistribution;
    }

    public Distribution getRedirectCfDistribution() {
        return redirectCfDistribution;
    }

    private void init() {
        // Cloudfront Certificate
        ICertificate cloudfrontCertificate = Certificate.fromCertificateArn(this, "bsn-cloudfront-certificate-arn",
                CLOUDFRONT_CERTIFICATE_ARN);

        //Main S3 Bucket
        Bucket mainBucket = Bucket.Builder.create(this, "bsn-main-s3")
                .bucketName("bsn-main")
                .websiteIndexDocument("index.html")
                .websiteErrorDocument("index.html")
                .blockPublicAccess(BlockPublicAccess.Builder.create()
                        .blockPublicAcls(false)
                        .blockPublicPolicy(false)
                        .ignorePublicAcls(false)
                        .restrictPublicBuckets(false)
                        .build())
                .publicReadAccess(true)
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        // Redirect Bucket
        Bucket redirectBucket = Bucket.Builder.create(this, "bsn-redirect-s3")
                .bucketName("bsn-redirect")
                .websiteRedirect(RedirectTarget.builder()
                        .hostName(WEB_SUBDOMAIN + "." + BSN_DOMAIN)
                        .protocol(RedirectProtocol.HTTPS)
                        .build())
                .blockPublicAccess(BlockPublicAccess.Builder.create()
                        .blockPublicAcls(false)
                        .blockPublicPolicy(false)
                        .ignorePublicAcls(false)
                        .restrictPublicBuckets(false)
                        .build())
                .publicReadAccess(true)
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        // Main CloudFront Distribution
        mainCfDistribution = Distribution.Builder.create(this, "bsn-main-cf-distribution")
                .domainNames(List.of(WEB_SUBDOMAIN + "." + BSN_DOMAIN))
                .defaultBehavior(BehaviorOptions.builder()
                        .origin(new S3StaticWebsiteOrigin(mainBucket))
                        .viewerProtocolPolicy(ViewerProtocolPolicy.REDIRECT_TO_HTTPS)
                        .build())
                .certificate(cloudfrontCertificate)
                .build();

        // Redirect CloudFront Distribution
        redirectCfDistribution = Distribution.Builder.create(this, "bsn-redirect-cf-distribution")
                .domainNames(List.of(BSN_DOMAIN))
                .defaultBehavior(BehaviorOptions.builder()
                        .origin(new S3StaticWebsiteOrigin(redirectBucket))
                        .viewerProtocolPolicy(ViewerProtocolPolicy.REDIRECT_TO_HTTPS)
                        .build())
                .certificate(cloudfrontCertificate)
                .build();
    }

}
