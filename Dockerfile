# Multi-stage build for Spring Boot
FROM maven:3.9.6-openjdk-17-slim AS build

# Set working directory
WORKDIR /app

# Copy pom.xml first for better caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file from build stage
COPY --from=build /app/target/microservice-proveedores-facturacion-1.0.0.jar app.jar

# Expose port
EXPOSE 8081

# Run the application
CMD ["java", "-jar", "app.jar"]
