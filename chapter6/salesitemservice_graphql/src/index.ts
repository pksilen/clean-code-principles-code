import { ApolloServer } from '@apollo/server';
import { startStandaloneServer } from '@apollo/server/standalone';
import GraphQlSalesItemController, {typeDefs} from "./controller/GraphQlSalesItemController";

const controller = new GraphQlSalesItemController(salesItemService);

const server = new ApolloServer({
    typeDefs,
    resolvers: controller.getResolvers()
});

const { url } = await startStandaloneServer(server, {
    listen: { port: 8000 },
});

