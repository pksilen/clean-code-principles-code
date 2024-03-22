import InputSalesItem from '../dtos/InputSalesItem';
import OutputSalesItem from '../dtos/OutputSalesItem';

export default interface SalesItemService {
  createSalesItem(inputSalesItem: InputSalesItem): Promise<OutputSalesItem>;

  getSalesItems(): Promise<OutputSalesItem[]>;

  getSalesItem(id: string): Promise<OutputSalesItem>;

  updateSalesItem(id: string, inputSalesItem: InputSalesItem): Promise<void>;

  deleteSalesItem(id: string): Promise<void>;
}
