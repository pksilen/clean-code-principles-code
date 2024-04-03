import SalesItemService from '../../services/SalesItemService';
import { transformAndValidate } from 'class-transformer-validator';
import InputSalesItem from '../../dtos/InputSalesItem';
import { createGrpcErrorResponse } from '../../common/utils/utils';

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

  private getSalesItems = async (rpc, callback) => {
    try {
      callback(null, {
        salesItems: await this.salesItemService.getSalesItems(),
      });
    } catch (error) {
      this.respondWithError(rpc.path, error, callback);
    }
  };

  private getSalesItem = async (rpc, callback) => {
    try {
      callback(null, await this.salesItemService.getSalesItem(rpc.request.id));
    } catch (error) {
      this.respondWithError(rpc.path, error, callback);
    }
  };

  private createSalesItem = async (rpc, callback) => {
    try {
      const inputSalesItem = await transformAndValidate(
        InputSalesItem,
        rpc.request as object,
      );

      callback(
        null,
        await this.salesItemService.createSalesItem(inputSalesItem),
      );
    } catch (error) {
      this.respondWithError(rpc.path, error, callback);
    }
  };

  private updateSalesItem = async (rpc, callback) => {
    try {
      const inputSalesItem = await transformAndValidate(
        InputSalesItem,
        rpc.request as object,
      );

      await this.salesItemService.updateSalesItem(
        rpc.request.id,
        inputSalesItem,
      );
      callback(null, undefined);
    } catch (error) {
      this.respondWithError(rpc.path, error, callback);
    }
  };

  private deleteSalesItem = async (rpc, callback) => {
    try {
      await this.salesItemService.deleteSalesItem(rpc.request.id);
      callback(null, undefined);
    } catch (error) {
      this.respondWithError(rpc.path, error, callback);
    }
  };

  private respondWithError(endpoint: string, error: Error, callback) {
    callback(createGrpcErrorResponse(endpoint, error));
  }
}
