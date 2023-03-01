FROM gradle:5.0.0-jdk11-slim AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:jre

RUN mkdir /app
WORKDIR /app
COPY ./bin ./bin
COPY ./.env ./.env
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/application.jar
ENTRYPOINT /app/bin/start.sh