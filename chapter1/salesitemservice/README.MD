# Prerequisites

- JDK 17
- Docker

# Start MySql

`docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -d mysql:latest`

# Create database

```
docker exec -it mysql bash
# The below command asks for the password which is 'password'
mysql -u root -p
mysql> create database public;
mysql> exit
exit
```

# Start Spring Boot Application

`./gradlew :bootRun`

or

`.\gradlew.bat :bootRun`

# Execute HTTP Requests
Example HTTP requests are available in scratches/rest-api.http file.
The file can be used e.g., with the HTTP client of Intellij IDEA Ultimate.