FROM openjdk:19
COPY ./text-matching-service.jar /

ENTRYPOINT java -jar -Dspring.profiles.active=docker /text-matching-service.jar
