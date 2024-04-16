import SalesItem from '../entities/SalesItem';
import InputSalesItem from '../dtos/InputSalesItem';
import { SortBy } from '../dtos/SalesItemsQuery';

export default interface SalesItemRepository {
  save(salesItem: SalesItem): Promise<void>;

  findAll(
    search: string | undefined,
    page: number,
    sortBy: SortBy,
  ): Promise<SalesItem[]>;

  find(id: string): Promise<SalesItem | null>;

  update(inputSalesItem: InputSalesItem): Promise<void>;

  delete(id: string): Promise<void>;
}
