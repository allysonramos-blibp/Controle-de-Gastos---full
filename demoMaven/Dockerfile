# Estágio de Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copia o conteúdo da pasta onde está o pom.xml
COPY . .
RUN mvn clean package -DskipTests

# Estágio de Execução
FROM eclipse-temurin:17-jdk
WORKDIR /app
# O comando abaixo busca qualquer jar dentro da pasta target
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Garante que o Spring Boot use a porta que a Render fornecer
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
