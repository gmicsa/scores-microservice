FROM openjdk:17.0.1-jdk-slim
VOLUME /tmp
ADD build/libs/scores-microservice-0.0.3.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]