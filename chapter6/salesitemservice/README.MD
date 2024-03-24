# Sales Item Service

## Prerequisites

- Node.js 18 or later
- Docker

## Install Dependencies

```bash
npm install
```

## Start MySQL and MongoDB

```bash
docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -d mysql:latest
docker run --name mongodb -p 27017:27017 -d mongo:latest
```

## Create MySQL Database

```bash
docker exec -it mysql bash
# The below command asks for the password which is 'password'
mysql -u root -p
mysql> create database salesitemservice;
mysql> exit
exit
```

## Start Nest.js Server

```bash
# Choose one of the below commands depending whether you want to use REST or GraphQL
export CONTROLLER_TYPE=rest
export CONTROLLER_TYPE=graphql

# Choose one of the below commands depending whether you want to use MySQL or MongoDB
export DATABASE_URL=mongodb://localhost:27017/salesitemservice
export DATABASE_URL=mysql+pymysql://root:password@localhost:3306/salesitemservice

# If you are using MySQL, choose one of the below commands depending whether 
# you want to use ORM or parameterized SQL
export REPOSITORY_TYPE=orm
export REPOSITORY_TYPE=paramsql

npm run start:dev
```

Sample HTTP requests are available in scratches/salesitemservice_rest.http and
scratches/salesitemservice_graphql.http files.
You can use these requests with the PyCharm Professional's HTTP client.
