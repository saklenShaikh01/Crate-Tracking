# Base image
FROM openjdk:17-jdk-slim

# Working directory
WORKDIR /app

# Copy only inventory jar
COPY . .

RUN chmod +x mvnw && ./mvnw clean install -Dmaven.test.skip=true

# Expose inventory port (example 8081)
EXPOSE 8081

# CMD to run inventory jar
CMD ["java", "-jar", "target/inventory-service-0.0.1-SNAPSHOT"]
