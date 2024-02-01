# Build stage
FROM maven:3.8.5-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Final stage
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/swoppiApp-0.0.1-SNAPSHOT.jar swoppiApp-demo.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "swoppiApp-demo.jar"]