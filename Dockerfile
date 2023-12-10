# Use the official Maven image as the base image
FROM maven:3.9.5-eclipse-temurin-21

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files into the container
COPY pom.xml /app/
COPY src /app/src/

# Run Maven to build the application
RUN mvn clean install

# Specify the command to run your application
CMD ["java", "-jar", "target/java-template-0.0.1.jar"]
