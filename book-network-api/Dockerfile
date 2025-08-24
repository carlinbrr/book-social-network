# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
#We already know the app has run the tests correctly
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine AS runtime
ARG APP_VERSION=1.0.0
ARG PROFILE=dev

WORKDIR /app
COPY --from=build ./build/target/book-network-api-*.jar .

EXPOSE 8088

ENV JAR_VERSION=${APP_VERSION}
ENV ACTIVE_PROFILE=${PROFILE}

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} book-network-api-${JAR_VERSION}.jar