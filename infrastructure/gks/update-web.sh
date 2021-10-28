#!/usr/bin/env bash

#push local image to Google container repo
gcloud docker -- push gcr.io/scores-microservice/scores-microservice:0.0.3

#upgrade web-controller to latest version of docker image
kubectl rolling-update web-controller --image=gcr.io/scores-microservice/scores-microservice:0.0.3 --image-pull-policy Always