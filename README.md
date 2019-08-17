# event-hub-publisher
This micro service is responsible for letting organizations to publish their consumer events.

From the following architecture diagram the purple highlighted service represents this micro service.

![alt text](Architecture.png)

## Running in local using Maven
mvn clean spring-boot:run -Dspring-boot.run.arguments=--server.port=8083,--spring.profiles.active=local

## Building docker
mvn compile jib:dockerBuild

## Pushing to container registry
mvn compile jib:build

## Deploying workload to GKE
kubectl apply -f deployment.yaml

## Deploying service to GKE
kubectl apply -f service.yaml
