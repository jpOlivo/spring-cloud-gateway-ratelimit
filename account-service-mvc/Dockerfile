FROM adoptopenjdk/openjdk11:jdk-11.0.6_10-alpine-slim
EXPOSE 8091
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY dd-java-agent.jar dd-java-agent.jar
ENTRYPOINT ["java","-javaagent:/dd-java-agent.jar","-jar","/app.jar"]
