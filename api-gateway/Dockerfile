# Stage 1: Build
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn verify clean --fail-never
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Run
FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
