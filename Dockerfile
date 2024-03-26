# Используем базовый образ Java 8
FROM openjdk:17-jdk

# Копируем jar файл в контейнер
COPY target/coba-1.0-SNAPSHOT-jar-with-dependencies.jar /app.jar

# Запускаем приложение
CMD ["java", "-cp", "/app.jar", "org.consumer.kafka.Main"]