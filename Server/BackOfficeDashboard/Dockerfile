# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container

COPY target/*.jar /app/app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "/app/app.jar"]
