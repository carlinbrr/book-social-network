package com.bsn.infra.cdk.config;

public class EnvironmentConfig {

    // Database Users
    public static final String ROOT_DB_USER = System.getenv("ROOT_DB_USER");
    public static final String API_DDL_USER = System.getenv("API_DDL_USER");
    public static final String API_DML_USER = System.getenv("API_DML_USER");
    public static final String KEYCLOAK_DB_USER = System.getenv("KEYCLOAK_DB_USER");

    // Certificates
    public static final String ALB_CERTIFICATE_ARN = System.getenv("ALB_CERTIFICATE_ARN");

    // Hosted Zone & Domain
    public static final String HOSTED_ZONE_ID = System.getenv("HOSTED_ZONE_ID");
    public static final String BSN_DOMAIN = System.getenv("BSN_DOMAIN");

    // Mail
    public static final String MAIL_HOST = System.getenv("MAIL_HOST");
    public static final String MAIL_PORT = System.getenv("MAIL_PORT");
    public static final String MAIL_USER =  System.getenv("MAIL_USER");
    public static final String MAIL_PASSWORD =  System.getenv("MAIL_PASSWORD");

    // URLs
    public static final String FRONTEND_URL = System.getenv("FRONTEND_URL");
    public static final String BSN_USERS_API_URL = System.getenv("BSN_USERS_API_URL");
    public static final String JWT_ISSUER_URI = System.getenv("JWT_ISSUER_URI");

    // Keycloak
    public static final String KEYCLOAK_HOST = System.getenv("KEYCLOAK_HOST");
    public static final String KEYCLOAK_ADMIN_USER = System.getenv("KEYCLOAK_ADMIN_USER");
    public static final String KEYCLOAK_ADMIN_PASSWORD = System.getenv("KEYCLOAK_ADMIN_PASSWORD");

}
