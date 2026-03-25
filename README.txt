Jakarta EE 11
https://jakarta.ee/release/11/

OAI-PMH
https://www.openarchives.org/pmh/

mvn dependency:get com.sun.xml.bind:jaxb-xjc:4.0.6

./jaxb-ri/bin/xjc.sh -encoding UTF-8 -d ./nefeli-oai-pmh/src/main/java/ https://www.openarchives.org/OAI/2.0/OAI-PMH.xsd

TODO
cleanup db
handle HTTP
202 Accepted
503 Service Unavailable
400 Bad Request

