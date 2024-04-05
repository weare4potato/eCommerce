FROM openjdk:17-alpine
LABEL authors="potato"

COPY ./build/libs/eCommerce-0.0.1-SNAPSHOT.jar potato.jar

ENTRYPOINT ["java","-jar","/potato.jar"]





