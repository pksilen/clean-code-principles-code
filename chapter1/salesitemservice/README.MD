# Start MySql


`docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -d mysql:latest`

# Create database

```
docker exec --it mysql bash
mysql -u root -p
create database public;
exit
exit
```

# Start Spring Boot Application

`./gradlew :bootRun`

or

`.\gradlew.bat :bootRun`

# Execute HTTP Requests
Example HTTP requests are available in scratches/rest-api.http file.