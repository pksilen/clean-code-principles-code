import InputSalesItem from '../dtos/InputSalesItem';
import OutputSalesItem from '../dtos/OutputSalesItem';

export default interface SalesItemService {
  createSalesItem(inputSalesItem: InputSalesItem): OutputSalesItem;

  getSalesItems(): OutputSalesItem[];

  getSalesItemsByUserAccountId(userAccountId: string): OutputSalesItem[];

  getSalesItem(id: string): OutputSalesItem;

  updateSalesItem(id: string, inputSalesItem: InputSalesItem): void;

  deleteSalesItem(id: string): void;
}
