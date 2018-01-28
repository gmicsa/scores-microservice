#!/usr/bin/env bash

# see https://medium.com/google-cloud/running-a-mean-stack-on-google-cloud-platform-with-kubernetes-149ca81c2b5d

# first install gcloud CLI and login

# create cluster
gcloud container --project "scores-microservice" clusters create "scores-cluster" --zone "europe-west3-a" --machine-type "g1-small" --num-nodes "2" --disk-size "20" --network "default"

# create disk to store MongoDB data
gcloud compute disks create --project "scores-microservice" --zone "europe-west3-a" --size 10GB mongo-disk