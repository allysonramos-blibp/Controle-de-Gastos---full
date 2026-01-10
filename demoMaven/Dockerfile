# Estágio de Build (Usa Maven com Eclipse Temurin, que é estável)
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Estágio de Execução (Usa a imagem oficial da Eclipse Temurin)
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
