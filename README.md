# event-hub-publisher
This micro service is responsible for letting organizations to publish their consumer events.

## Building docker
mvn compile jib:dockerBuild

## Pushing to container registry
mvn compile jib:build

## Deploying to GKE
kubectl apply -f deployment
