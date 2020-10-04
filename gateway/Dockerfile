FROM adoptopenjdk/openjdk11:jdk-11.0.6_10-alpine-slim
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
