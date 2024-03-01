const express = require('express');
const { graphqlHTTP } = require('express-graphql');
const { buildSchema, GraphQLError } = require('graphql');
const axios = require('axios').default;

const schema = buildSchema(`
  type UserAccount {
    id: ID!,
    userName: String!
    # Define additional properties...
  }

  type SalesItem {
    id: ID!,
    name: String!
    # Define additional properties...
  }

  type Order {
    id: ID!,
    userId: ID!
    # Define additional properties...
  }

  type User {
    userAccount: UserAccount!
    salesItems: [SalesItem!]!
    orders: [Order!]!
  }

  type Query {
    user(id: ID!): User!
  }
`);

const {
    ORDER_SERVICE_URL,
    SALES_ITEM_SERVICE_URL,
    USER_ACCOUNT_SERVICE_URL
} = process.env;

const rootValue = {
    user: async ({ id }) => {
        try {
            const [
                { data: userAccount },
                { data: salesItems },
                { data: orders }
            ] = await Promise.all([
                axios.get(`${USER_ACCOUNT_SERVICE_URL}/user-accounts/${id}`),
                axios.get(
                    `${SALES_ITEM_SERVICE_URL}/sales-items?userAccountId=${id}`
                ),
                axios.get(`${ORDER_SERVICE_URL}/orders?userAccountId=${id}`)
            ]);

            return {
                userAccount,
                salesItems,
                orders
            };
        } catch (error) {
            throw new GraphQLError(error.message);
        }
    },
};

const app = express();

app.use('/graphql', graphqlHTTP({
    schema,
    rootValue,
    graphiql: true,
}));

app.listen(4000);