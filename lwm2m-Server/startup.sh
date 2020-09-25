#!/bin/sh
echo "files copy /lwm2m-Server/html/* to /data/web_service"
/bin/cp -fR ~/lwm2m-Server/html/* /data/web_service
echo "lwm2m-Server server is start!!"
java -jar lwm2m-Server-0.0.1-SNAPSHOT.jar &