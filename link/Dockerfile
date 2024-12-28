FROM openjdk:23
COPY target/*.jar app.jar
COPY config.json config.json
ENTRYPOINT ["java","-jar","/app.jar"]