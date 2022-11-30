FROM openjdk:20-bullseye
LABEL maintainer="Grzegorz Kowalczyk"
VOLUME /main-app
ADD build/libs/generate-number-app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]

