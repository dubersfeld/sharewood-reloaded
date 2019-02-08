#!/bin/sh
echo "********************************************************"
echo "Starting Sharewood Configuration Server"
echo "********************************************************"

java -Djava.security.egd=file:/dev/./urandom -jar /app.jar
