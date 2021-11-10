# scores-microservice

A reactive microservice for managing football scores developed with Java 17, Spring Boot 2.5, Spring WebFlux, Spring Data Reactive Repositories and backed by MongoDB.

### Run locally:
#### Prerequisites:
- Java 17
- Npm 5.5.1
- Node v8.9.0
- Angular CLI (ng): 6.0.7 Sorry for using old version of Angular, this has been done initially in 2018 and things are moving very fast in NG world :))
- Docker Desktop
- minikube

#### How to build project locally?
Run `./infrastructure/dev/build.sh`. This will build and package the Angular app, build and package the Java app 
and create the Docker image `scores-microservice`.

#### Run locally using Docker Compose:
Docker Compose is used to run app composed by Spring Web and MongoDB containers locally. See scripts inside `/infrastructure/dev/docker-compose` folder for running.

#### Run locally on Kubernetes using Minikube:
- Scripts are located in folder `/infrastructure/dev/k8s`
- Run `./create-volumes.sh`. This will create the volume and volume claim needed by Mongo so that data is persisted even if you recreate Mongo container.
- Run `./start.sh`. This will create deployments and services for the scores microservice and Mongo DB
- Run `kubectl get all` to check everything started ok
- Run `minikube tunnel` to create a tunnel to our service
- Access frontend at `http://localhost:8080/scores` Enjoy!
- To clean-up resources, you can run: `./destroy-all.sh` (this will remove services and deployments) and `destroy-volumes.sh` (this will remove volumes, your saved data will be lost)


### Run on Google Cloud:
App can be deployed to Google Kubernetes Engine using configuration files and scripts located inside 
`/infrastructure/gke` folder.