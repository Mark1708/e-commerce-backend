FROM openjdk:11-jre-slim
LABEL maintainer="mark.gurianov@gmail.com"
COPY ./build/libs/config-service-0.0.1-SNAPSHOT.jar ./
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar ./config-service-0.0.1-SNAPSHOT.jar"]