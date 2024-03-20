import express from 'express';
import cors from 'cors';
import { authorizer } from './jwt_authorizer';

const app = express();
app.use(express.json());
app.use(cors());

// @ts-ignore
app.use((error, request, response, next) => {
  // Error handler for converting a thrown error
  // to an error response
  // Example of an error handler implementation
  // is given in API principles chapter
});

app.get('/api/sales-item-service/sales-items', () => {
  // No authorization needed
  // Send sales items
});

app.post('/api/messaging-service/messages', async (request, response) => {
  await authorizer.authorize(request.headers.authorization);
  // Authorized user can create a message
  console.log('Message created');
});

app.get('/api/order-service/orders/:id', async (request, response) => {
  const userIdFromJwt = await authorizer.getUserId(request.headers.authorization);
  const id = request.params.id;

  // Try get order using 'userIdFromJwt' as user id and 'id'
  // as order id, e.g. orderService.getOrder(id, userIdFromJwt)
  // It is important to notice that when trying to retrieve
  // the order from database, both 'id' and 'userIdFromJwt'
  // are used as query filters
  // If the user is not allowed to access the resource
  // 404 Not Found is returned from the service method
  // This approach has the security benefit of not revealing
  // to an attacker whether an order with 'id' exists or not
});

app.post('/api/order-service/orders', async (request, response) => {
  await authorizer.authorizeForSelf(
    request.body.userId,
    request.headers.authorization
  );

  // Create an order for the user
  // User cannot create orders for other users
});

app.delete('/api/sales-item-service/sales-items', async (request, response) => {
  await authorizer.authorizeIfUserHasOneOfRoles(
    ['admin'],
    request.headers.authorization
  );

  // Only admin user can delete all sales items
});

app.listen(8000);
