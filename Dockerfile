FROM maven:3.8.5-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/signature-service-0.0.1-SNAPSHOT.jar app.jar

# Определение переменных среды с безопасными дефолтными значениями
ENV APP_TOKEN=default-token-value
ENV SIGNATURE_SECRET=default-signature-secret

# Опционально: можно настроить порт, на котором приложение будет работать
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"] 