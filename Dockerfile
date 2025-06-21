# Use official OpenJDK image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy JAR file (update filename to your actual JAR)
COPY target/OnlineBookStore-1.0.jar app.jar

# Expose port (must match your Spring Boot server.port)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
