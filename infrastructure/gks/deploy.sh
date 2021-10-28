#!/usr/bin/env bash

#push local image to Google container repo
gcloud docker -- push gcr.io/scores-microservice/scores-microservice:0.0.3

# create mongo controller and service
kubectl create -f mongo-controller.yml
kubectl create -f mongo-service.yml

#create web replication controller and load balancer
kubectl create -f web-controller.yml
kubectl create -f web-service.yml

#list pods
kubectl get pods

#get load balancer public IP
gcloud compute forwarding-rules list