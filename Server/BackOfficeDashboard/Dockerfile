# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine AS build

# Set working directory for the build
WORKDIR /build

# Install Maven and required tools
RUN apk add --no-cache maven

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the project
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# Use a smaller runtime image for the final build
FROM openjdk:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /build/target/*.jar /app/app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "/app/app.jar"]
