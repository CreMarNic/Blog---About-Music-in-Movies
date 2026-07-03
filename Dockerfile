# Multi-stage build for Spring Boot backend
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first for dependency caching
COPY backend/pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY backend/src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/blog-api-1.0.0.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["sh", "-c", "if [ -n \"$DATABASE_URL\" ] && [ -z \"$SPRING_DATASOURCE_URL\" ]; then export SPRING_DATASOURCE_URL=\"jdbc:${DATABASE_URL}\"; fi; exec java -jar app.jar"]
