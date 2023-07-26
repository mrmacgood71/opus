
#
# Build stage
#
FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-alpine
COPY --from=build /target/OPUS-0.2.0-SNAPSHOT.jar opuserver.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "opuserver.jar"]