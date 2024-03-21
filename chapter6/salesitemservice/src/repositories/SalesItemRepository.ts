import SalesItem from '../entities/SalesItem';
import InputSalesItem from '../dtos/InputSalesItem';

export default interface SalesItemRepository {
  save(salesItem: SalesItem): Promise<void>;

  findAll(): Promise<SalesItem[]>;

  findByUserAccountId(userAccountId: string): Promise<SalesItem[]>;

  find(id: string): Promise<SalesItem | null>;

  update(id: string, inputSalesItem: InputSalesItem): Promise<void>;

  delete(id: string): Promise<void>;
}
