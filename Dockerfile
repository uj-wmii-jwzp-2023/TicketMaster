ARG JAVA_VER="17"

# STEP 1: Build JAR with Gradle
FROM gradle:7.6.1-jdk${JAVA_VER}-alpine AS BUILD
WORKDIR /home/gradle/project

COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src
RUN gradle bootJar

# STEP 2: Build app image
FROM eclipse-temurin:${JAVA_VER}-jdk-alpine
VOLUME /tmp
COPY --from=BUILD /home/gradle/project/build/libs/ticketmaster-*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080
