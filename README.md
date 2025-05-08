# Signature Service

Сервис для генерации HMAC SHA256 подписей из параметров формы.

## Требования

- Java 11 или выше
- Maven 3.6 или выше
- Docker (опционально)

## Сборка и запуск

### Локальная сборка

1. Клонируйте репозиторий:
```bash
git clone https://github.com/andrewpolewoy/paysonix.git
cd paysonix
```

2. Соберите проект:
```bash
./mvnw clean package
```

3. Запустите приложение:
```bash
java -jar target/signature-service-0.0.1-SNAPSHOT.jar
```

### Запуск через Docker

1. Соберите Docker-образ:
```bash
docker build -t paysonix/signature-service:latest .
```

2. Запустите контейнер:
```bash
docker run -p 8080:8080 \
  -e APP_TOKEN=your-token \
  -e SIGNATURE_SECRET=your-secret \
  paysonix/signature-service:latest
```

## Конфигурация

Приложение использует следующие переменные окружения:

- `APP_TOKEN` - токен для аутентификации запросов (по умолчанию: "test-token")
- `SIGNATURE_SECRET` - секретный ключ для генерации подписи (по умолчанию: "test-secret")

## API

### Генерация подписи

```http
POST /api/v1/signature/{operationId}
Token: your-token
Content-Type: application/x-www-form-urlencoded

param1=value1&param2=value2
```

#### Параметры

- `operationId` - идентификатор операции
- `formParams` - параметры формы для генерации подписи

#### Ответ

```json
{
  "status": "success",
  "result": [
    {
      "signature": "generated-signature"
    }
  ]
}
```

## Тестирование

Запуск тестов:
```bash
./mvnw test
```

## Безопасность

- Все запросы должны содержать валидный токен в заголовке `Token`
- Подписи генерируются с использованием HMAC SHA256
- Секретный ключ для генерации подписи хранится в переменных окружения

## Логирование

Приложение использует SLF4J с Logback для логирования. Уровни логирования настраиваются в `application.yml`.

## Мониторинг

Доступны эндпоинты Actuator:
- `/actuator/health` - проверка состояния приложения
- `/actuator/info` - информация о приложении

## Лицензия

MIT 