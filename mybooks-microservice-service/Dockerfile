FROM openjdk:8-jre

WORKDIR /opt/app
ARG JAR_FILE
COPY ${JAR_FILE} /opt/app/app.jar

CMD java -jar /opt/app/app.jar