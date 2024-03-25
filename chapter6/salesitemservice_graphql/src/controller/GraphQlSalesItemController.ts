
export const typeDefs =
`
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

input InputImage {
  rank: Int!
  url: String!
}

input InputSalesItem {
  name: String!
  priceInCents: Int!
  images: [InputImage!]!
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
`

export default class GraphQlSalesItemController {
    constructor(salesItemService) {
        this.salesItemService = salesItemService;
    }

    getResolvers() {
        return {
            Query: {
                salesItems: this.getSalesItems,
                salesItem: this.getSalesItem
            },
            Mutation: {
                createSalesItem: this.createSalesItem,
                updateSalesItem: this.updateSalesItem,
                deleteSalesItem: this.deleteSalesItem
            }
        }
    }

    private getSalesItems(...) {
        return this.salesItemService.getSalesItems(...);
    }

    private getSalesItem({ id }) {
        return this.salesItemService.getSalesItem(id);
    }

    private createSalesItem({ inputSalesItem: input }) {
        const inputSalesItem = await transformAndValidate(InputSalesItem, input);
        return this.salesItemService.createSalesItem(inputSalesItem);
    }

    private updateSalesItem({ id, inputSalesItem: input }) {
        const inputSalesItem = await transformAndValidate(InputSalesItem, input);
        return this.salesItemService.createSalesItem(newSalesItem);
    }

    private deleteSalesItem({ id }) {
        this.salesItemService.deleteSalesItem(id);
        return { id };
    }
}
