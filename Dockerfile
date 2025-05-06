# Build
FROM eclipse-temurin:21-jdk-jammy as build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean install -DskipTests && \
    apt-get purge -y maven && \
    apt-get autoremove -y && \
    rm -rf /var/lib/apt/lists/*

# Produção
FROM eclipse-temurin:21-jre-jammy

EXPOSE 8080
WORKDIR /app
COPY --from=build /app/target/api_fluxo-*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]