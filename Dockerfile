FROM openjdk:20-bullseye
ADD build/libs/generate-number-app-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar generate-number-app-0.0.1-SNAPSHOT.jar

