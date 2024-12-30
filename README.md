# Механизм сокращения ссылок

## Зависимости 
- Docker Compose
- JDK
- Maven
- Go
- Make

## Составные части
- link
  - Сервер сервиса, написан на java
- cli
  - Клиент сервиса, написан на Go
- db
  - PostgreSQL с хранилищем сокращенных ссылок

## Начало работы
- создать .env файлы из example.env файлов в директориях
- make build
- make build-cli
- make up
