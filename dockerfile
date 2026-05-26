FROM ubuntu:22.04 AS build

RUN apt-get update
RUN apt-get install -y openjdk-17-jdk
COPY . .

RUN apt-get install -y maven
RUN mvn clean install

FROM openjdk:17-jdk-slim AS runtime
EXPOSE 8080

COPY --from=build /target/gestao-vagas-0.0.1-SNAPSHOT.jar /app/gestao-vagas-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/gestao-vagas-0.0.1-SNAPSHOT.jar"]