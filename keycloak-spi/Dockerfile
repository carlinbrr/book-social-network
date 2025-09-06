# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM quay.io/keycloak/keycloak:26.2.0 AS runtime

COPY --from=build /build/target/keycloak-spi*.jar /opt/keycloak/providers/keycloak-spi.jar

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev"]