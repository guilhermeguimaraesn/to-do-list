# Etapa de construção (build)
FROM ubuntu:latest AS build

# Atualiza o cache do apt e instala o JDK 17
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Configura o diretório de trabalho no contêiner
WORKDIR /app

# Copia todos os arquivos do diretório atual para o contêiner
COPY . .

# Instala o Maven e compila o projeto
RUN apt-get install -y maven && mvn clean install

# Etapa de produção
FROM openjdk:17-jdk-slim

# Define a porta que o aplicativo irá expor
EXPOSE 8080

# Configura o diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo JAR do estágio de construção para o contêiner
COPY --from=build /app/target/todolist-1.0.0.jar app.jar

# Comando de entrada para iniciar o aplicativo quando o contêiner for iniciado
ENTRYPOINT ["java", "-jar", "app.jar"]
