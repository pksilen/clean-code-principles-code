# Prerequisites

- JDK 17
- Docker

# Start MySql and MongoDB

`docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -d mysql:latest`
`docker run --name mongodb -p 27017:27017 -d mongo:latest`

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

Before starting the application, specify which database type to use: "sql" or "mongodb"
in `Application.java` file. Initial value is "sql".


`./gradlew :bootRun`

or

`.\gradlew.bat :bootRun`

# Execute REST HTTP Requests
Example HTTP requests are available in scratches/orderservice_rest.http file.
The file can be used e.g., with the HTTP client of Intellij IDEA Ultimate.

# Execute GraphQL HTTP Requests
Example HTTP requests are available in scratches/orderservice_graphql.http file.
The file can be used e.g., with the HTTP client of Intellij IDEA Ultimate.