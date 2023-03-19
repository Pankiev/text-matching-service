#!/usr/bin/env bash
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd "$SCRIPT_DIR" || (echo "Error while changing directory" && exit)

chmod +x ../mvnw
bash ../mvnw package -f ../pom.xml || (echo "Error while executing mvn package" && exit)
cp ../target/text-matching-0.0.1-SNAPSHOT.jar ./docker/text-matching-service.jar
rm ./docker/text-matching-service.jar
cd ./docker || (echo "Error while changing directory" && exit)
docker build . -t text-matching-service:latest
