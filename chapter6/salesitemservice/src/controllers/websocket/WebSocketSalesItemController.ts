import {
  MessageBody,
  SubscribeMessage,
  WebSocketGateway,
} from '@nestjs/websockets';
import InputSalesItem from '../../dtos/InputSalesItem';
import SalesItemService from '../../services/SalesItemService';
import { Inject, UseFilters, UseInterceptors } from '@nestjs/common';
import { transformAndValidate } from 'class-transformer-validator';
import { WebSocketErrorFilter } from './WebSocketErrorFilter';
import { WebSocketRequestTracer } from './WebSocketRequestTracer';
import { SalesItemsQuery } from '../../dtos/SalesItemsQuery';

@UseInterceptors(WebSocketRequestTracer)
@UseFilters(new WebSocketErrorFilter())
@WebSocketGateway(8001, { cors: true })
export default class WebSocketSalesItemController {
  constructor(
    @Inject('salesItemService')
    private readonly salesItemService: SalesItemService,
  ) {}

  @SubscribeMessage('createSalesItem')
  async createSalesItem(@MessageBody() data: object) {
    const inputSalesItem = await transformAndValidate(InputSalesItem, data);
    return this.salesItemService.createSalesItem(inputSalesItem);
  }

  @SubscribeMessage('getSalesItems')
  async getSalesItems(@MessageBody() data: object) {
    const salesItemsQuery = await transformAndValidate(SalesItemsQuery, data);

    return this.salesItemService.getSalesItems(
      salesItemsQuery.nameContains,
      salesItemsQuery.page,
      salesItemsQuery.sortBy,
    );
  }

  @SubscribeMessage('getSalesItem')
  getSalesItem(@MessageBody() data: string) {
    return this.salesItemService.getSalesItem(data);
  }

  @SubscribeMessage('updateSalesItem')
  async updateSalesItem(@MessageBody() data: object) {
    const inputSalesItem = await transformAndValidate(InputSalesItem, data);

    await this.salesItemService.updateSalesItem(
      (inputSalesItem as any).id,
      inputSalesItem,
    );

    return '';
  }

  @SubscribeMessage('deleteSalesItem')
  async deleteSalesItem(@MessageBody() data: string) {
    await this.salesItemService.deleteSalesItem(data);
    return '';
  }
}
