FROM openjdk:8-jdk-alpine
ADD build/libs/generate-number-app-0.0.1-SNAPSHOT.jar generate-number-app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]

