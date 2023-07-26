
#
# Build stage
#
FROM maven:3.8.1-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-alpine
COPY --from=build /target/OPUS-0.2.0-SNAPSHOT.jar opuserver.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "opuserver.jar"]