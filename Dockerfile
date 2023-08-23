# Use a base image with Java and Gradle
FROM gradle:8.3-jdk17 AS build

# Set the working directory
WORKDIR /app

# Copy the project's files to the container
COPY . .

# Build the Spring Boot application using Gradle
RUN gradle clean build -x test

# Start the Spring Boot application when the container starts
CMD ["java", "-jar", "/app/build/libs/PingPongBot-1.0.jar"]