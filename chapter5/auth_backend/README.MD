# Authorization Backend

## Prerequisites

- Node.js v18 or later

## Install Dependencies

```bash
npm install
```

## Start Keycloak

```bash
docker-compose up
```

## Login to Keycloak

Login at: localhost:8080

Username: user
Password: bitnami

## Create client

1. Create new client here http://localhost:8080/admin/master/console/#/master/clients by clicking *Create client* button
2. For *Client ID*, supply 'app-x'
3. Press *Next* two times
4. For *Valid redirect URIs*, supply http://127.0.0.1:5173/auth
5. For *Valid post logout redirect URIs*, supply http://127.0.0.1:5173
6. For *Web origins*, supply http://127.0.0.1:5173
7. Press *Save*

## Build Server

```bash
npm run build
```

## Start Server

```bash
export OIDC_CONFIG_URL=http://localhost:8080/realms/master/.well-known/openid-configuration
export JWT_ROLES_CLAIM_PATH=real_access.roles

# Below service does not really exists but we must give some value to
# environment variable
export GET_USERS_URL=http://localhost:8082/user-service/users

npm start
```



