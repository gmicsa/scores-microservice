#!/usr/bin/env bash

echo 'Cleaning'
cd ../..
./gradlew clean
rm -Rf scores-frontend/dist

echo 'Building Angular app'
cd scores-frontend
ng build -prod

cd ..

echo 'Copy Angular resources'
mkdir -p build/resources/main/static
cp scores-frontend/dist/* build/resources/main/static

echo 'Building Spring Boot app'
./gradlew build buildDocker

echo 'Tagging GCR image'
docker tag gmicsa/scores-microservice:0.0.2 gcr.io/scores-microservice/scores-microservice:0.0.2