FROM maven:3.9-eclipse-temurin-21 AS dependencies
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY --from=dependencies /root/.m2 /root/.m2
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre AS execution
WORKDIR /home/fiap
RUN adduser fiap --disabled-password
USER fiap
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]