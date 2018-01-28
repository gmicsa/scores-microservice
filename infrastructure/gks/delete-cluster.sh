#!/usr/bin/env bash

gcloud container clusters delete "scores-cluster" --zone "europe-west3-a" --quiet

gcloud compute disks delete --project "scores-microservice" --zone "europe-west3-a" mongo-disk --quiet

gcloud container images delete gcr.io/scores-microservice/scores-microservice:0.0.1 --force-delete-tags --quiet