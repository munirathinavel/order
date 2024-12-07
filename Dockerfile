# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY target/order-0.0.1-SNAPSHOT.war app.jar

ENV NEW_RELIC_API_KEY=b443650ee5199df4a5ba381768ab9414FFFFNRAL
ENV NEW_RELIC_ACCOUNT_ID=6241005
ENV NEW_RELIC_APP_NAME=OrderService

# Expose the application port
EXPOSE 8014

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
