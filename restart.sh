#!/usr/bin/env bash

#docker stop nefeli-web
#docker rm nefeli-web
#docker rmi nefeli-web:1.0.0-SNAPSHOT
docker compose --project-directory ./ --file ./src/main/docker/compose.yaml down --rmi all
mvn clean install
docker compose --project-directory ./ --file ./src/main/docker/compose.yaml up --detach
docker exec --interactive --tty nefeli-web bash
