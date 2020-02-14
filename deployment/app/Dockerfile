FROM openjdk:8-jdk-alpine

COPY easydocker-0.0.1-SNAPSHOT.jar app.jar
RUN ls
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]