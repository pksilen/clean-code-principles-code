import { Args, Mutation, Query, Resolver } from '@nestjs/graphql';
import { Inject, UseInterceptors } from '@nestjs/common';
import OutputSalesItem from '../../dtos/OutputSalesItem';
import SalesItemService from '../../services/SalesItemService';
import InputSalesItem from '../../dtos/InputSalesItem';
import IdResponse from './IdResponse';
import { GraphQlRequestTracer } from './GraphQlRequestTracer';

@UseInterceptors(GraphQlRequestTracer)
@Resolver()
export default class GraphQlSalesItemController {
  constructor(
    @Inject('salesItemService')
    private readonly salesItemService: SalesItemService,
  ) {}

  @Query(() => [OutputSalesItem])
  async salesItems() {
    return await this.salesItemService.getSalesItems();
  }

  @Query(() => OutputSalesItem)
  async salesItem(@Args('id') id: string) {
    return await this.salesItemService.getSalesItem(id);
  }

  @Mutation(() => OutputSalesItem)
  async createSalesItem(
    @Args('inputSalesItem')
    inputSalesItem: InputSalesItem,
  ) {
    return this.salesItemService.createSalesItem(inputSalesItem);
  }

  @Mutation(() => IdResponse)
  async updateSalesItem(
    @Args('id') id: string,
    @Args('inputSalesItem') inputSalesItem: InputSalesItem,
  ) {
    await this.salesItemService.updateSalesItem(id, inputSalesItem);
    return { id };
  }

  @Mutation(() => IdResponse)
  async deleteSalesItem(@Args('id') id: string) {
    await this.salesItemService.deleteSalesItem(id);
    return { id };
  }
}
