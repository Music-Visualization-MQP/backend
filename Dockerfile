# Build stage with Gradle
FROM eclipse-temurin:17-jdk-jammy AS build
RUN apt-get update && \
    apt-get install -y gradle && \
    rm -rf /var/lib/apt/lists/*
# Copy source code to the container
COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src/app

# Build the application
RUN gradle clean build --info

# Package stage with JRE
FROM build

WORKDIR /app

# Copy only the artifacts we need from the builder stage and discard the rest
COPY --from=build /home/gradle/src/app/build/libs/*.jar /app/spring-boot-application.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "spring-boot-application.jar"]