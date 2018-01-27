#!/usr/bin/env bash

for run in {1..20}
do
    # please change to your load balancer IP
    out=$(curl -s http://admin:admin2018@IP/actuator/env | grep -Po '"value":.*?[^\\]",' | sed -n '28p')
    echo "${out:9:20}"
done