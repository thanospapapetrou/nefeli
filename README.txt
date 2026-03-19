Jakarta EE 11
https://jakarta.ee/release/11/

OAI-PMH
https://www.openarchives.org/pmh/

mvn dependency:get com.sun.xml.bind:jaxb-xjc:4.0.6

./jaxb-ri/bin/xjc.sh -encoding UTF-8 -d ./nefeli-oai-pmh/src/main/java/ https://www.openarchives.org/OAI/2.0/OAI-PMH.xsd

docker build -t nefeli-web:1.0.0-SNAPSHOT -f ./nefeli-web/src/main/docker/Dockerfile ./
docker run --publish 8080:8080 --detach --rm --name nefeli-web nefeli-web:1.0.0-SNAPSHOT
docker exec -it nefeli-web bash
docker stop nefeli-web
docker rmi nefeli-web:1.0.0-SNAPSHOT

docker stop nefeli-web; docker rmi nefeli-web:1.0.0-SNAPSHOT; mvn clean verify; docker build -t nefeli-web:1.0.0-SNAPSHOT -f ./nefeli-web/src/main/docker/Dockerfile ./; docker run --publish 8080:8080 --detach --rm --name nefeli-web nefeli-web:1.0.0-SNAPSHOT; docker exec -it nefeli-web bash

docker build -t nefeli-db:1.0.0-SNAPSHOT -f ./nefeli-db/src/main/docker/Dockerfile ./
docker run --publish 5432:5432 --detach --rm --name nefeli-db nefeli-db:1.0.0-SNAPSHOT

docker exec -it nefeli-db bash
docker stop nefeli-db
docker rmi nefeli-db:1.0.0-SNAPSHOT
