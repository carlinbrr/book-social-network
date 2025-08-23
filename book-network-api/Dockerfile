# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
#We already know the app has run the tests correctly
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:21-jdk AS runtime
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build ./build/target/book-network-api-*.jar /app/

EXPOSE 8088

ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} book-network-api-${JAR_VERSION}.jar
