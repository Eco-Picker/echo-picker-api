FROM openjdk:17-jdk-alpine

ENV JAVA_APP_DIR=/home/app \
    JAVA_OPTIONS="-Dfile.encoding=utf-8"

WORKDIR ${JAVA_APP_DIR}

COPY ./build/libs/eco-picker-api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
