FROM maven:3.9.7-sapmachine-17 AS build

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

#ENV DB_ADDRESS=172.17.0.3
#ENV DB_PORT=5432
# ENV DB_NAME=
# ENV JWT_SECRET=
#ENV CORS_ORIGIN=172.17.0.1

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]