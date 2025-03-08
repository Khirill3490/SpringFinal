FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/finalSpring-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "sleep 20 && java -jar app.jar"]