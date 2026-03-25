FROM tomcat:11.0.18

RUN cp -r /usr/local/tomcat/webapps.dist/host-manager/  /usr/local/tomcat/webapps/
RUN cp -r /usr/local/tomcat/webapps.dist/manager/  /usr/local/tomcat/webapps/
COPY ./src/main/docker/host-manager-context.xml /usr/local/tomcat/webapps/host-manager/META-INF/context.xml
COPY ./src/main/docker/manager-context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
COPY ./src/main/docker/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY ./target/nefeli-web-1.0.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
ENV CATALINA_OPTS="$CATALINA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,address=*:5000,suspend=n"
