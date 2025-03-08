# Final Spring Boot Application

Spring Boot приложение, для бронирования/поиска/фильтрации комнат в отелях разных городов, которое можно запустить локально или в Docker.

## Как запустить
### В Docker-контейнере

1. Соберите проект командой
    ```bash
   ./gradlew build

2. Зайдите в директорию c файлом [docker-compose.yaml](docker/forDocker/docker-compose.yaml) командой
    ```bash
   cd docker/fordocker

3. Запустите проект командой
   ```bash
   docker compose up --build

### Локально

1. В файле [application.yaml](src/main/resources/application.yaml) установить 
   active: local
2. Перейти в директорию [docker](docker) командой

   ```bash
   cd docker
   
3. Запусить проект командой
   ```bash
   ./docker-start.sh    
