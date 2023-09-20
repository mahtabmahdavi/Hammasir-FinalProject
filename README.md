# Routing Report API

### Authentication:
In this section, we have two roles, **ADMIN** and **USER**. Users and operators can login and those who sign up can only have **USER** role.
- Sign up:
```
curl --location 'localhost:8080/authentication/signup' \
--header 'Content-Type: application/json' \
--data '{
    "phoneNumber": "09102587557",
    "username": "saranayebi",
    "password": "57pp324"
}'
```
- Log in:
```
curl --location 'localhost:8080/authentication/login' \
--header 'Content-Type: application/json' \
--data '{
    "username": "sinahosseini",
    "password": "852ss456"
}'
```
---

### Report:
- Registering reports with the sender's user information:
```
curl --location 'localhost:8080/reports/create' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYXJhbmF5ZWJpIiwiaWF0IjoxNjk1MTA2NjIwLCJleHAiOjE2OTUxOTMwMjB9.wS_2lsANQ-S_uJDJE_qBWUHlBZG8j6upH4EMnDzV3zE' \
--data '{
    "type": "traffic",
    "category": "SMOOTH",
    "location": "POINT (59.55835029724793 36.31614408459342)"
}'
```
- Display active reports available along the user path:
```
curl --location --request GET 'localhost:8080/reports/active' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaW5haG9zc2VpbmkiLCJpYXQiOjE2OTUxMDkxMTgsImV4cCI6MTY5NTE5NTUxOH0._3T5sGnB649bG2pugahcnWdsaDIJCYhaAT_DhBkOVRg' \
--data '{
    "location": "LINESTRING (59.56013728955995 36.31488691847986, 59.55829146544451 36.31612634019682, 59.56040647224415 36.31807838944107)"
}'
```
- Users can like and dislike reports and **Like** increases report life and ****DisLike**** decreases the life of the report:
```
curl --location --request PUT 'localhost:8080/reports/like' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYXJhbmF5ZWJpIiwiaWF0IjoxNjk1MjI0NzY4LCJleHAiOjE2OTUzMTExNjh9.ho6G941w1nq9pJGciOPi1LuA6TMpm896-ghy7huuubQ' \
--data '{
    "type": "traffic",
    "reportId": 29,
    "status": true
}'
```
- Operator approval for some reports that require approval:
```
curl --location --request PUT 'localhost:8080/reports/approve' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWh0YWJtYWhkYXZpIiwiaWF0IjoxNjk1MjA1OTU3LCJleHAiOjE2OTUyOTIzNTd9.x84pgOOZ27vQJiWF9OVMleFNS85IFd4EM7T5YA_YWsg' \
--data '{
    "type": "bug",
    "reportId": 20,
    "status": false
}'
```
- The most accidental hour:
```
curl --location 'localhost:8080/reports/most-accidental' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYXJhbmF5ZWJpIiwiaWF0IjoxNjk1MTUxOTA5LCJleHAiOjE2OTUyMzgzMDl9.hgCKOVgw8I7PQC5sS-Mwx-blpIfeifIrSoksWME_M4o'
```