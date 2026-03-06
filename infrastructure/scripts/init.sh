#!/bin/bash

set -e

# Specify environment variables
export API_DDL_USER=root
export API_DML_USER=bsn
export KEYCLOAK_DB_USER=keycloak
export MAIL_HOST=smtp.gmail.com
export MAIL_PORT=465
export MAIL_USER=carlosdev050@gmail.com
export MAIL_PASSWORD="sfos rxef bmqt vctn"
export JWT_ISSUER_URI=https://auth.cdcollaguazo.com/realms/book-social-network
export APP_FRONTEND_HOST=https://www.cdcollaguazo.com

# Compile and run
cd ..
./mvnw clean install -DSkipTests
java -cp /c/Users/Carlitos/.m2/repository/software/amazon/awscdk/aws-cdk-lib/2.240.0/aws-cdk-lib-2.240.0.jar:target/classes com.bsn.infrastructure.InfrastructureApp
exit 0

aws cloudformation delete-stack --stack-name NetworkStack
aws cloudformation delete-stack --stack-name BackendStack

# Run deploy command
aws cloudformation deploy --stack-name NetworkStack --template-file cdk.out/NetworkStack.template.json
aws cloudformation deploy --stack-name BackendStack --template-file cdk.out/BackendStack.template.json --capabilities CAPABILITY_IAM
