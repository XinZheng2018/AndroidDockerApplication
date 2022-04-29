#!/bin/bash
# Change the configuration of Tomcat so that it listens to
# the port assigned by Heroku
sed -i s/8080/$PORT/ /usr/local/tomcat/conf/server.xml
# delete the default ROOT directory so ROOT.war is used
rm -rf /usr/local/tomcat/webapps/ROOT
# start the server
catalina.sh run