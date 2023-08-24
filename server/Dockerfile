FROM tomcat:9-jdk17-openjdk
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY out/artifacts/server_war/server_war.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]