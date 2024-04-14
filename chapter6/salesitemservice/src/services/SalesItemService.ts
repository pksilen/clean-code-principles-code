import InputSalesItem from '../dtos/InputSalesItem';
import OutputSalesItem from '../dtos/OutputSalesItem';
import { SortBy } from '../dtos/SalesItemsQuery';

export default interface SalesItemService {
  createSalesItem(inputSalesItem: InputSalesItem): Promise<OutputSalesItem>;

  getSalesItems(
    search: string | undefined,
    page: number,
    sortBy: SortBy,
  ): Promise<OutputSalesItem[]>;

  getSalesItem(id: string): Promise<OutputSalesItem>;

  updateSalesItem(id: string, inputSalesItem: InputSalesItem): Promise<void>;

  deleteSalesItem(id: string): Promise<void>;
}
