import SalesItemService from '../../services/SalesItemService';
import { transformAndValidate } from 'class-transformer-validator';
import InputSalesItem from '../../dtos/InputSalesItem';

export default class GrpcSalesItemController {
  constructor(private readonly salesItemService: SalesItemService) {}

  getRequestHandlers() {
    return {
      getSalesItems: this.getSalesItems,
      getSalesItem: this.getSalesItem,
      createSalesItem: this.createSalesItem,
      updateSalesItem: this.updateSalesItem,
      deleteSalesItem: this.deleteSalesItem,
    };
  }

  private getSalesItems = async (rpc, callback) =>
    callback(null, { salesItems: await this.salesItemService.getSalesItems() });

  private getSalesItem = async (rpc, callback) =>
    callback(null, await this.salesItemService.getSalesItem(rpc.request.id));

  private createSalesItem = async (rpc, callback) => {
    const inputSalesItem = await transformAndValidate(
      InputSalesItem,
      rpc.request as object,
    );

    callback(null, await this.salesItemService.createSalesItem(inputSalesItem));
  };

  private updateSalesItem = async (rpc, callback) => {
    const inputSalesItem = await transformAndValidate(
      InputSalesItem,
      rpc.request as object,
    );

    await this.salesItemService.updateSalesItem(rpc.request.id, inputSalesItem);
    callback(null, undefined);
  };

  private deleteSalesItem = async (rpc, callback) => {
    await this.salesItemService.deleteSalesItem(rpc.request.id);
    callback(null, undefined);
  };
}
