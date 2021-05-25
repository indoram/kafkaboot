FROM openjdk:8
COPY target/kafkaboot-0.0.1-SNAPSHOT.war kafkaboot-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java", "-jar", "kafkaboot-0.0.1-SNAPSHOT.war"]
EXPOSE 8081