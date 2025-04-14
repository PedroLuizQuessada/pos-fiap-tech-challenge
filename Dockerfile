FROM maven:3.9.9-amazoncorretto-21-alpine AS build

WORKDIR /home/fiap

RUN adduser fiap -D

USER fiap

COPY src ./src

COPY pom.xml .

RUN mvn -f ./pom.xml clean package -DskipTests

ENTRYPOINT ["java","-jar","./target/tech-challenge-0.0.1-SNAPSHOT.jar"]