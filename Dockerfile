FROM openjdk:8-jdk-alpine

RUN apk update && apk add --no-cache tesseract-ocr

# RUN addgroup -S spring && adduser -S spring -G spring
# USER spring:spring

COPY eng.traineddata app/eng.traineddata

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

ENV LC_ALL=C
ENV TESSDATA_PREFIX=/app