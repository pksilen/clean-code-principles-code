import {
  Body,
  Controller,
  Delete,
  Get,
  HttpCode,
  Inject,
  Param,
  Post,
  Put,
  UseGuards,
  UseInterceptors,
} from '@nestjs/common';
import SalesItemService from '../services/SalesItemService';
import InputSalesItem from '../dtos/InputSalesItem';
import OutputSalesItem from '../dtos/OutputSalesItem';
import { AuditLogger } from '../interceptors/AuditLogger';
import { RequestCounter } from '../interceptors/RequestCounter';
import { RequestTracer } from '../interceptors/RequestTracer';
import { AllowForUserThatHasOneOfRoles } from '../guards/AllowForUserThatHasOneOfRoles';
import { authorizer } from '../common/authorizer/FakeAuthorizer';

@UseInterceptors(RequestCounter, RequestTracer)
@Controller('sales-items')
export default class RestSalesItemController {
  constructor(
    @Inject('salesItemService')
    private readonly salesItemService: SalesItemService,
  ) {}

  @Post()
  createSalesItem(
    @Body() inputSalesItem: InputSalesItem,
  ): Promise<OutputSalesItem> {
    return this.salesItemService.createSalesItem(inputSalesItem);
  }

  @Get()
  getSalesItems() // @Query('userAccountId') userAccountId: string,
  : Promise<OutputSalesItem[]> {
    return this.salesItemService.getSalesItems();
  }

  @Get('/:id')
  getSalesItem(@Param('id') id: string): Promise<OutputSalesItem> {
    return this.salesItemService.getSalesItem(id);
  }

  @Put('/:id')
  @HttpCode(204)
  updateSalesItem(
    @Param('id') id: string,
    @Body() inputSalesItem: InputSalesItem,
  ): Promise<void> {
    return this.salesItemService.updateSalesItem(id, inputSalesItem);
  }

  @UseGuards(new AllowForUserThatHasOneOfRoles(['admin'], authorizer))
  @UseInterceptors(AuditLogger)
  @Delete('/:id')
  @HttpCode(204)
  deleteSalesItem(@Param('id') id: string): Promise<void> {
    return this.salesItemService.deleteSalesItem(id);
  }
}
