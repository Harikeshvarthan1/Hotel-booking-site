# Stage 1: Build the application -  multi stage docker for lean build

FROM gradle:8.4-jdk21 AS builder
 
# Copy project files to container

COPY . /app
 
# Run Gradle build - no test done to minimise build time

RUN cd /app && gradle clean build -x test
 
# Stage 2: Run the application - trusted eclipse temurin image for slimmer faster and secure image

FROM eclipse-temurin:21-jdk-jammy
 
# Copy the JAR from the builder stage

COPY --from=builder /app/build/libs/*.jar /app/app.jar
 
# Expose application port (optional, e.g., 8080)

EXPOSE 8080
 
# Run the JAR

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
 
