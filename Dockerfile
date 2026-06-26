################################### RAILWAY ########################################################
## Estágio 1: Build da aplicação
#FROM maven:3.9.6-eclipse-temurin-17 AS build
#WORKDIR /app
#
## Copia os arquivos de configuração do Maven e o código fonte
#COPY pom.xml .
#COPY src ./src
#
## Executa o build gerando o arquivo .jar (pulando os testes para acelerar o deploy)
#RUN mvn clean package -DskipTests
#
## Estágio 2: Execução da aplicação
#FROM eclipse-temurin:17-jdk-alpine
#WORKDIR /app
#
## Copia o jar gerado no estágio anterior
#COPY --from=build /app/target/*.jar app.jar
#
## Define a porta dinâmica exigida pela Railway
#EXPOSE 8080
#
## Comando para rodar a API
#ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-jar", "app.jar"]

################################### RENDER ########################################################

# ─── Estágio 1: Build ────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copia o wrapper e o pom primeiro (cache de dependências)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B

# Copia o código e gera o .jar
COPY src src
RUN ./mvnw clean package -DskipTests

# ─── Estágio 2: Runtime ──────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Render injeta PORT automaticamente; 8080 é o fallback local
EXPOSE 8080

ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-jar", "app.jar"]