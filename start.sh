#!/bin/bash
docker-compose up -d
echo Waiting for postgresql container to start
sleep 10

./mvnw clean install
./mvnw spring-boot:run
