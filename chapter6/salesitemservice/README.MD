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

## Start Nest.js REST/GraphQL/WebSocket Server

```bash
# Choose one of the below commands depending whether you want to use REST, GraphQL or WebSocket
export CONTROLLER_TYPE=rest
export CONTROLLER_TYPE=graphql
export CONTROLLER_TYPE=websocket

# Choose one of the below commands depending whether you want to use MySQL or MongoDB
export DATABASE_URL=mongodb://localhost:27017/salesitemservice
export DATABASE_URL=mysql://root:password@localhost:3306/salesitemservice

# If you are using MySQL, choose one of the below commands depending whether 
# you want to use ORM or parameterized SQL
export REPOSITORY_TYPE=typeorm
export REPOSITORY_TYPE=prismaorm
export REPOSITORY_TYPE=paramsql

prisma generate --schema=./src/repositories/orm/prisma/schema.prisma
prisma migrate dev --name init --schema=./src/repositories/orm/prisma/schema.prisma
npm run start:dev
```

Sample HTTP requests are available in scratches/salesitemservice_rest.http and
scratches/salesitemservice_graphql.http files.
You can use these requests with the WebStorm's HTTP client.

## Start gRPC Server

gRPC server has currently fixed implementation that works only with MySQL and PrismaORM.

```bash
export DATABASE_URL=mysql://root:password@localhost:3306/salesitemservice
npm run build
node dist/grpcServer.js
```

You can use the server with the client in another terminal window:

```bash
node dist/grpcClient.js
```
