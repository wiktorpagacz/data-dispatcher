FROM openjdk:19-oracle
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "application.jar"]