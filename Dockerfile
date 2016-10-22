FROM anapsix/alpine-java:8
VOLUME /tmp
ADD build/libs/scores-microservice-0.0.1.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]