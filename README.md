Выполнено согласно тз.
todo - тесты + ошибки
# Getting Started
Создать бд 'demo' через интерфейс или через докер как показано ниже
todo определиться с бд и убрать из помника лишнее
```shell
docker run --name demo-mysql  -p 3306:3306  -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=user -e MYSQL_PASSWORD=password -e MYSQL_DATABASE=demo -d percona:ps-8.0.36-28
```
или
```shell
docker run -it --name trekker -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=trekker -d postgres:11-alpine
```
### Reference Documentation

* БД MySql -> таблица users + таблица tasks + таблица comments.

#### Примеры запросов:

Регистрация
```shell
curl -i -X POST http://127.0.0.1:8080/api/auth/register -H 'Content-Type: application/json' -d '{"username":"role","email":"role@mail.ru","password":"12345678"}'
```

Авторизация
```shell
curl -i -X POST http://127.0.0.1:8080/api/auth/login -H 'Content-Type: application/json' -d '{"email":"role@mail.ru" , "password":"12345678"}'
```

Далее уже авторизованным пользователям:
 
Создать таск
```shell
curl -i -X POST http://127.0.0.1:8080/tasks/great -H 'Content-Type: application/json' -d '{"status":"status" , "contractor":"Papa", "description":"mini","priority":"hi"}' \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDAwLCJlbWFpbCI6InJvbGVAbWFpbC5ydSIsInN1YiI6InJvbGUiLCJpYXQiOjE3MjMxMzkyMzYsImV4cCI6MTcyMzI4MzIzNn0.n7djYMrHeKKwlqFG6C-zWxXSnw0UxjzVnl2sngM-DTw" \
  -H "Application-Authorization: token"
```
Получить задачу
```shell
curl -i -X GET http://localhost:8080/tasks/100001 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDAwLCJlbWFpbCI6InJvbGVAbWFpbC5ydSIsInN1YiI6InJvbGUiLCJpYXQiOjE3MjMxMzYwNTMsImV4cCI6MTcyMzI4MDA1M30.fxYJ3mRthznjrtinZJAoKPOsaUWw8HcunxXkig3oQL8" \
  -H "Application-Authorization: token"
```
Получить задачи по имени создателя c фильтром по приоритету(остальные фильтры также работают)
```shell
curl -i -X GET http://localhost:8080/tasks/creator \
  -H 'Content-Type: application/json' -d '{"name":"role" ,"filterPriority":"hi"}' \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDAwLCJlbWFpbCI6InJvbGVAbWFpbC5ydSIsInN1YiI6InJvbGUiLCJpYXQiOjE3MjMxMzkyMzYsImV4cCI6MTcyMzI4MzIzNn0.n7djYMrHeKKwlqFG6C-zWxXSnw0UxjzVnl2sngM-DTw" \
  -H "Application-Authorization: token"
```
Получить задачи по имени исполнителя c фильтром по description(остальные фильтры также работают)
```shell
curl -i -X GET "http://localhost:8080/tasks/contractor?&limit=2&offset=0" \
  -H 'Content-Type: application/json' -d '{"name":"Papa" , "filterDescription":"mini"}' \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDAwLCJlbWFpbCI6InJvbGVAbWFpbC5ydSIsInN1YiI6InJvbGUiLCJpYXQiOjE3MjMxMzkyMzYsImV4cCI6MTcyMzI4MzIzNn0.n7djYMrHeKKwlqFG6C-zWxXSnw0UxjzVnl2sngM-DTw" \
  -H "Application-Authorization: token"
```
Удалить задачу
```shell
curl -i -X DELETE http://localhost:8080/tasks/100004 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDAwLCJlbWFpbCI6InJvbGVAbWFpbC5ydSIsInN1YiI6InJvbGUiLCJpYXQiOjE3MjMxMzYwNTMsImV4cCI6MTcyMzI4MDA1M30.fxYJ3mRthznjrtinZJAoKPOsaUWw8HcunxXkig3oQL8" \
  -H "Application-Authorization: token"
```
Создать комментарий
```shell
curl -i -X POST http://127.0.0.1:8080/comment/great -H 'Content-Type: application/json' -d '{ "text":"contractor CommentServiceCommon", "taskId":"100001"}' \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJpZCI6MTAwMDAwLCJlbWFpbCI6InJvbGVAbWFpbC5ydSIsInN1YiI6InJvbGUiLCJpYXQiOjE3MjMxMzUyOTQsImV4cCI6MTcyMzI3OTI5NH0.PIj9u1kTl4AMuK-jPvmqM2ukm7VjelSXUwi4Mk_G4vs" \
  -H "Application-Authorization: token"
```