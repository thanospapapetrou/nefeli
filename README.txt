Jakarta EE 11
https://jakarta.ee/release/11/

OAI-PMH
https://www.openarchives.org/pmh/

mvn dependency:get com.sun.xml.bind:jaxb-xjc:4.0.6

./jaxb-ri/bin/xjc.sh -encoding UTF-8 -d ./nefeli-oai-pmh/src/main/java/ https://www.openarchives.org/OAI/2.0/OAI-PMH.xsd

TODO
cleanup db
move impl classes to packages not exported by modules
container -> content
handle HTTP
202 Accepted
503 Service Unavailable
400 Bad Request

handle HTTP 429 Too Many Requests -> Retry-After (seconds)
handle HTTP 503 Service Unavailable -> Retry-After (seconds)
Retry-After: <http-date>
Retry-After: <delay-seconds>
handle redirects
http://ejournal.uin-suka.ac.id/tarbiyah/index.php/alathfal/oai

replace URL equals
cleanup harvester
cleanup client
cleanup DAO
why marshallers and unmarshallers don't have a schema?
{http://www.language-archives.org/OLAC/1.1/olac-archive}olac-archive

maven-jar-plugin should be configured in a single pom (preferably parent)
package names for containers
*Content -> Container
