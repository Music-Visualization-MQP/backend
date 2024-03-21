
# Build stage with Gradle
FROM eclipse-temurin:17-jdk-jammy AS setup
RUN apt-get update && \
    apt-get install -y gradle && \
    rm -rf /var/lib/apt/lists/*
# Copy source code to the container
COPY --chown=gradle:gradle . /home/gradle/src

RUN export GRADLE_OPTS="-Xmx4g -Xms1g -XX:MaxMetaspaceSize=1g" &&\
export JAVA_OPTS="-Xmx4g -Xms1g -XX:MaxMetaspaceSize=1g"

# Build the application
FROM gradle:8.0-jdk17-jammy AS builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build --no-daemon --parallel

# Package stage with JRE
FROM eclipse-temurin:17-jdk-jammy
RUN addgroup --system userapp && \
        adduser -system --no-create-home --uid 1001 userapp --ingroup userapp


WORKDIR /app

# Copy only the artifacts we need from the builder stage and discard the rest
COPY --from=builder /app /app

# Expose the port your app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app/build/libs/app-1.0.jar"]