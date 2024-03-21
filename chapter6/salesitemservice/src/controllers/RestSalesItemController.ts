import {
  Controller,
  Get,
  Query,
  Post,
  Body,
  Put,
  Param,
  Delete
} from '@nestjs/common';
import SalesItemService from '../SalesItemService';

@Controller('sales-items')
export default class RestSalesItemController {
  constructor(private readonly salesItemService: SalesItemService) {}

  @Post()
  createSalesItem(
    @Body() inputSalesItem: InputSalesItem
  ): Promise<OutputSalesItem> {
    return this.salesItemService.createSalesItem(inputSalesItem);
  }

  @Get()
  getSalesItems(
    @Query('userAccountId') userAccountId: string,
  ): Promise<OutputSalesItem[]> {
    if (userAccountId) {
      return this.salesItemService.getSalesItemsByUserAccountId
      (
        userAccountId
      );
    } else {
      return this.salesItemService.getSalesItems();
    }
  }

  @Get('/:id')
  getSalesItem((@Param('id') id: string): Promise<OutputSalesItem> {
    return this.salesItemService.getSalesItem(id);
  }

  @Put('/:id')
  @HttpCode(204)
  updateSalesItem(
    @Param('id') id: string,
    @Body() inputSalesItemArg: InputSalesItem
  ):Promise<void> {
    return this.salesItemService.updateSalesItem(id, inputSalesItem);
  }

  @Delete('/:id')
  @HttpCode(204)
  deleteSalesItem(@Param('id') id: string): Promise<void> {
    return this.salesItemService.deleteSalesItem(id);
  }

  @Delete()
  @HttpCode(204)
  deleteSalesItems(): Promise<void> {
    return this.salesItemService.deleteSalesItems();
  }
}
