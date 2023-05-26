FROM maven:3.6.3-openjdk-17 AS MAVEN_BUILD

WORKDIR /build

ADD . .

RUN mvn package

FROM openjdk:17

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/ozan-exchange*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
