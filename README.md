Выполнено согласно тз.
todo - тесты + ошибки
# Getting Started
Запустить docker-compose
```shell
docker-compose up
```
или как указано ниже
Создать бд через докер
```shell
docker run -it --name trekker -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=trekker -d postgres:11-alpine
```
Установить мавен(если есть то пропустить этот пункт)
```shell
sudo apt-get install maven
```
Собрать исполняемый файл jar
```shell
mvn -f pom.xml clean package -D  maven.test.skip=false 
```
Запустить приложение
```shell
java -jar target/work-0.0.1-SNAPSHOT.jar --status=running
```

### Reference Documentation

* БД -> таблица users + таблица tasks + таблица comments.

#### Примеры запросов:
Регистрация
```shell
curl -i -X POST http://127.0.0.1:8080/api/auth/register \
 -H 'Content-Type: application/json' -d '{"username":"Me","email":"me@mail.ru","password":"12345678"}'
```

Авторизация
```shell
curl -i -X POST http://127.0.0.1:8080/api/auth/login \
 -H 'Content-Type: application/json' -d '{"email":"me@mail.ru" , "password":"12345678"}'
```


Далее уже авторизованным пользователям:
 
Создать таск
```shell
curl -i -X POST http://127.0.0.1:8080/tasks/great -H 'Content-Type: application/json' -d '{"status":"GREAT" , "contractor":"Papa", "description":"mini quest","priority":"HIGH"}' \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDIwLCJlbWFpbCI6Im1lMUBtYWlsLnJ1Iiwic3ViIjoiTWUxIiwiaWF0IjoxNzIzMjExNDU3LCJleHAiOjE3MjMzNTU0NTd9.rJ17Mfb_NaIWVZVyHON-18WTOGhnHXjQoS9Ih49MuRo" \
  -H "Application-Authorization: token"
```
Изменить таск
```shell
curl -i -X PUT http://127.0.0.1:8080/tasks/update -H 'Content-Type: application/json' -d '{"id":"100002" ,"status":"GREAT" , "contractor":"Papa", "description":"TaskRequestForUpdate","priority":"LOW"}' \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDAwLCJlbWFpbCI6Im1lQG1haWwucnUiLCJzdWIiOiJNZSIsImlhdCI6MTcyMzIxMDAyNCwiZXhwIjoxNzIzMzU0MDI0fQ.cPWnkZzxkwxgElmWOb2yxCXSvVOCJzN2xYIaX3BTljc" \
  -H "Application-Authorization: token"
```
Получить задачу
```shell
curl -i -X GET http://localhost:8080/tasks/100002 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDAwLCJlbWFpbCI6Im1lQG1haWwucnUiLCJzdWIiOiJNZSIsImlhdCI6MTcyMzIxMDAyNCwiZXhwIjoxNzIzMzU0MDI0fQ.cPWnkZzxkwxgElmWOb2yxCXSvVOCJzN2xYIaX3BTljc" \
  -H "Application-Authorization: token"
```
Получить задачи по имени создателя c фильтром по приоритету(остальные фильтры также работают)
```shell
curl -i -X GET http://localhost:8080/tasks/creator \
  -H 'Content-Type: application/json' -d '{"name":"Me" ,"filterPriority":"HIGH"}' \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDAwLCJlbWFpbCI6Im1lQG1haWwucnUiLCJzdWIiOiJNZSIsImlhdCI6MTcyMzIxMDAyNCwiZXhwIjoxNzIzMzU0MDI0fQ.cPWnkZzxkwxgElmWOb2yxCXSvVOCJzN2xYIaX3BTljc" \
  -H "Application-Authorization: token"
```
Получить задачи по имени исполнителя c фильтром по description(остальные фильтры также работают)
```shell
curl -i -X GET "http://localhost:8080/tasks/contractor?&limit=2&offset=0" \
  -H 'Content-Type: application/json' -d '{"name":"Papa" , "filterDescription":"mini"}' \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDAwLCJlbWFpbCI6Im1lQG1haWwucnUiLCJzdWIiOiJNZSIsImlhdCI6MTcyMzIxMDAyNCwiZXhwIjoxNzIzMzU0MDI0fQ.cPWnkZzxkwxgElmWOb2yxCXSvVOCJzN2xYIaX3BTljc" \
  -H "Application-Authorization: token"
```
Удалить задачу
```shell
curl -i -X DELETE http://localhost:8080/tasks/100001 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDAwLCJlbWFpbCI6Im1lQG1haWwucnUiLCJzdWIiOiJNZSIsImlhdCI6MTcyMzIxMDAyNCwiZXhwIjoxNzIzMzU0MDI0fQ.cPWnkZzxkwxgElmWOb2yxCXSvVOCJzN2xYIaX3BTljc" \
  -H "Application-Authorization: token"
```
Создать комментарий
```shell
curl -i -X POST http://127.0.0.1:8080/comment/great -H 'Content-Type: application/json' -d '{ "text":"Все сложно!", "taskId":"100002"}' \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDAwLCJlbWFpbCI6Im1lQG1haWwucnUiLCJzdWIiOiJNZSIsImlhdCI6MTcyMzIxMDAyNCwiZXhwIjoxNzIzMzU0MDI0fQ.cPWnkZzxkwxgElmWOb2yxCXSvVOCJzN2xYIaX3BTljc" \
  -H "Application-Authorization: token"
```