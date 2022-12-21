FROM adoptopenjdk/openjdk11:alpine

LABEL maintainer="zakhar.borisov@mail.ru"

ARG JAR_FILE=build/libs/giphy-currency-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY ${JAR_FILE} giphy-currency.jar

ENTRYPOINT ["java", "-jar", "giphy-currency.jar"]