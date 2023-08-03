FROM gradle:7.6.0-jdk11 as builder
WORKDIR /workspace/app
COPY . .
RUN ./gradlew clean build

FROM openjdk:11
COPY --from=builder /workspace/app/build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
