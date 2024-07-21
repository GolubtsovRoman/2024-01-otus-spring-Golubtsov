FROM maven:3.8.5-openjdk-17 AS builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM bellsoft/liberica-openjdk-alpine:17
ARG JAR_NAME="employee-management.jar"
COPY --from=builder /usr/src/app/target/$JAR_NAME /usr/app/$JAR_NAME
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/app/employee-management.jar"]



## https://github.com/pgpbpadilla/maven-multi-module-docker-cache/blob/main/docker/user-app.dockerfile

## https://github.com/Steven-Nagie/multi-module-spring-docker/blob/master/Dockerfile

## -> https://stackoverflow.com/questions/51679363/multi-module-maven-project-on-dockers