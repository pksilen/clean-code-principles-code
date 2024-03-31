# Chat Messaging Server

## Prerequisites

- Node.js 18 or later
- Docker

## Install Dependencies

```bash
npm install
```

## Start Redis and Kafka

```bash
docker run --name redis -p 6379:6379 -d redis/redis-stack:latest

# Start Kafka
docker-compose up
```

## Start Chat Messaging Server
```bash
export KAFKA_BROKERS=localhost:9094
npm run build
npm start
```

