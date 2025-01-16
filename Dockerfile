
# Docker  file multistage build
FROM maven:3.6.3-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests


# Stage 2 - Create the final image
FROM  azul/zulu-openjdk:17.0.13-jre

# Set the working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]