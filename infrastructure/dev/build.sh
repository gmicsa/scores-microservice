#!/usr/bin/env bash

cd ../..

./gradlew clean build buildDocker

 docker tag gmicsa/scores-microservice:0.0.1 gcr.io/scores-microservice/scores-microservice:0.0.1