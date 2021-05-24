FROM openjdk:8
COPY target/kafkaboot-0.0.1-SNAPSHOT.jar kafkaboot-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/kafkaboot-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081