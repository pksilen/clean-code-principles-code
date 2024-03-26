import SalesItemService from "../services/SalesItemService";
import { transformAndValidate } from "class-transformer-validator";
import InputSalesItem from "../dtos/InputSalesItem";

export const typeDefs = `
type SalesItem {
  id: ID!
  createdAtTimestampInMs: String!
  name: String!
  priceInCents: Int!
  images: [Image!]!
}

type Image {
  id: ID!
  rank: Int!
  url: String!
}

input InputSalesItem {
  name: String!
  priceInCents: Int!
  images: [InputImage!]!
}

input InputImage {
  rank: Int!
  url: String!
}

type IdResponse {
  id: ID!
}

type Query {
  salesItems: [SalesItem!]!
  salesItem(id: ID!): SalesItem!
}

type Mutation {
  createSalesItem(inputSalesItem: InputSalesItem!): SalesItem!

  updateSalesItem(
    id: ID!,
    inputSalesItem: InputSalesItem
  ): IdResponse!

  deleteSalesItem(id: ID!): IdResponse!
}
`;

export default class GraphQlSalesItemController {
  constructor(private readonly salesItemService: SalesItemService) {}

  getResolvers() {
    return {
      Query: {
        salesItems: this.getSalesItems,
        salesItem: this.getSalesItem,
      },
      Mutation: {
        createSalesItem: this.createSalesItem,
        updateSalesItem: this.updateSalesItem,
        deleteSalesItem: this.deleteSalesItem,
      },
    };
  }

  private getSalesItems = () => this.salesItemService.getSalesItems();
  private getSalesItem = (_, { id }) => this.salesItemService.getSalesItem(id);

  private createSalesItem = async (_, { inputSalesItem: input }) => {
    const inputSalesItem = await transformAndValidate(
      InputSalesItem,
      input as object,
    );

    return this.salesItemService.createSalesItem(inputSalesItem);
  };

  private updateSalesItem = async (_, { id, inputSalesItem: input }) => {
    const inputSalesItem = await transformAndValidate(
      InputSalesItem,
      input as object,
    );

    this.salesItemService.updateSalesItem(id, inputSalesItem);
    return { id };
  };

  private deleteSalesItem = (_, { id }) => {
    this.salesItemService.deleteSalesItem(id);
    return { id };
  };
}
