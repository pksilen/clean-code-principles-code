import SalesItem from '../entities/SalesItem';
import InputSalesItem from '../dtos/InputSalesItem';

export default interface SalesItemRepository {
  save(salesItem: SalesItem): SalesItem;

  findAll(): SalesItem[];

  findByUserAccountId(userAccountId: string): SalesItem[];

  find(id: string): SalesItem | null;

  update(id: string, inputSalesItem: InputSalesItem): void;

  delete(id: string): void;
}
