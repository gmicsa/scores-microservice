FROM openjdk:10.0.1-jre-slim
VOLUME /tmp
ADD scores-microservice-0.0.2.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]