FROM eclipse-temurin:21-jdk AS build

WORKDIR /app
COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app
EXPOSE 8080

COPY --from=build /app/target/gestao_vagas-0.0.1-SNAPSHOT.jar ./gestao-vagas.jar

ENTRYPOINT ["java", "-jar", "gestao-vagas.jar"]