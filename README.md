# event-hub-publisher
This micro service is responsible for letting organizations to publish their consumer events.

From the following architecture daigram the purple highlighted service represents this micro service.

![alt text](Architecture.png)
## Building docker
mvn compile jib:dockerBuild

## Pushing to container registry
mvn compile jib:build

## Deploying to GKE
kubectl apply -f deployment
