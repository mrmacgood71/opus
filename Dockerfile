FROM openjdk:17-alpine
MAINTAINER Churyumov Bogdan
ADD /target/OPUS-0.2.0-SNAPSHOT.jar opuserver.jar
ENTRYPOINT ["java", "-jar", "opuserver.jar"]
