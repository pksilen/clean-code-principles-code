import "reflect-metadata";
import { ApolloServer } from "@apollo/server";
import { startStandaloneServer } from "@apollo/server/standalone";
import GraphQlSalesItemController, {
  typeDefs,
} from "./controller/GraphQlSalesItemController";
import SalesItemServiceImpl from "./services/SalesItemServiceImpl";
import PrismaOrmSalesItemRepository from "./repositories/orm/prisma/PrismaOrmSalesItemRepository";
import SalesItemServiceError from "./errors/SalesItemServiceError";
import { unwrapResolverError } from "@apollo/server/errors";
import { createErrorResponse } from "./utils/utils";

const salesItemRepository = new PrismaOrmSalesItemRepository();
const salesItemService = new SalesItemServiceImpl(salesItemRepository);
const controller = new GraphQlSalesItemController(salesItemService);

const server = new ApolloServer({
  typeDefs,
  resolvers: controller.getResolvers(),
  formatError: (formattedError, graphQlError) => {
    const error = unwrapResolverError(graphQlError);
    const endpoint = formattedError.path?.[0];

    if (error instanceof SalesItemServiceError) {
      const errorResponse = (error as SalesItemServiceError).toResponse(
        endpoint,
      );

      return {
        message: errorResponse.errorMessage,
        extensions: errorResponse,
      };
    } else if ((error as any).constructor.name === "NonErrorThrown") {
      const errorResponse = createErrorResponse(
        new Error((error as any).thrownValue[0].toString()),
        400,
        "RequestValidationError",
        endpoint,
      );

      return {
        message: "Request validation error",
        extensions: errorResponse,
      };
    } else if (error instanceof Error) {
      const errorResponse = createErrorResponse(
        error,
        500,
        "UnspecifiedInternalError",
        endpoint,
      );

      return {
        message: "Unspecified internal error",
        extensions: errorResponse,
      };
    }

    return formattedError;
  },
});

startStandaloneServer(server, {
  listen: { port: 8000 },
});
