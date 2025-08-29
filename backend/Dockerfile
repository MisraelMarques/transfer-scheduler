
# Build stage
FROM gradle:7.5.1-jdk11 AS build
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

# Run stage
FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=build /app/build/libs/transfer-scheduler-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
