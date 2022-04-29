# Use Tomcat 10, which supports Servlets 5
FROM tomcat:10.1.0-M5-jdk16-openjdk-slim-bullseye

# This provides better support for running the JVM in a container
ENV JAVA_OPTS="-Xmx300m"

# Expose port 8080 when running on localhost
EXPOSE 8080

# Copy in our ROOT.war to the right place in the container
COPY ROOT.war /usr/local/tomcat/webapps/

# LOCALHOST:  Run catalina in the container
# Comment the next line out if running on Heroku
#CMD ["catalina.sh", "run"]
COPY tomcat_starter.sh /home/
CMD chmod +x /home/tomcat_starter.sh; /home/tomcat_starter.sh

# HEROKU: Run catalina in a container on Heroku
# Copy tomcat_starter.sh to the container; will executed when deployed
# Comment the next two lines if running on Localhost
# COPY tomcat_starter.sh /home/
# CMD chmod +x /home/tomcat_starter.sh; /home/tomcat_starter.sh
