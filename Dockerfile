FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /home/app

COPY src ./src

COPY pom.xml .

RUN mvn -f ./pom.xml clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java","-jar","./target/tech-challenge-0.0.1-SNAPSHOT.jar"]