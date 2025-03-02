FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar

COPY src/main/resources/keystore/keystore.p12 /app/keystore.12

#ENV DB_ADDRESS=172.17.0.2
#ENV DB_PORT=5432
#ENV DB_NAME=
#ENV JWT_SECRET=
#ENV CORS_ORIGIN=172.17.0.1

EXPOSE 8443

ENTRYPOINT ["java","-jar","app.jar"]