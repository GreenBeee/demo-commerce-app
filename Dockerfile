FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt/app
ARG JAR_FILE=target/demo*.jar

COPY ${JAR_FILE} demo-app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","demo-app.jar"]