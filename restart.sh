#!/usr/bin/env bash

docker compose --project-directory ./ --file ./src/main/docker/compose.yaml down --rmi all
mvn clean install
docker compose --project-directory ./ --file ./src/main/docker/compose.yaml up --detach
docker exec --interactive --tty nefeli-web bash
