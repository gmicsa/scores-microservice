#!/usr/bin/env bash

echo 'Cleaning'
cd ../..
./gradlew clean
rm -Rf scores-frontend/dist

echo 'Building Angular app'
cd scores-frontend
ng build --prod

cd ..

echo 'Copy Angular resources'
mkdir -p build/resources/main/static
cp -r scores-frontend/dist/* build/resources/main/static

echo 'Use Docker daemon from Minikube in order to have access to this image from Minikube'
eval $(minikube docker-env)

echo 'Building Spring Boot app'
./gradlew build docker

echo 'Tagging GCR image'
docker tag gmicsa/scores-microservice:0.0.3 gcr.io/scores-microservice/scores-microservice:0.0.3