import SalesItemService from './SalesItemService';
import InputSalesItem from '../dtos/InputSalesItem';
import OutputSalesItem from '../dtos/OutputSalesItem';
import SalesItem from '../entities/SalesItem';
import SalesItemRepository from '../repositories/SalesItemRepository';

export default class SalesItemServiceImpl implements SalesItemService {
  constructor(private readonly salesItemRepository: SalesItemRepository) {}

  createSalesItem(inputSalesItem: InputSalesItem): OutputSalesItem {
    let salesItem = SalesItem.from(inputSalesItem);
    salesItem = this.salesItemRepository.save(salesItem);
    return OutputSalesItem.from(salesItem);
  }

  getSalesItems(): OutputSalesItem[] {
    const salesItems = this.salesItemRepository.findAll();
    return salesItems.map((salesItem) => OutputSalesItem.from(salesItem));
  }

  getSalesItemsByUserAccountId(userAccountId: string): OutputSalesItem[] {
    const salesItems =
      this.salesItemRepository.findByUserAccountId(userAccountId);

    return salesItems.map((salesItem) => OutputSalesItem.from(salesItem));
  }

  getSalesItem(id: string): OutputSalesItem {
    const salesItem = this.salesItemRepository.find(id);

    if (!salesItem) {
      throw new EntityNotFoundError('Sales item', id);
    }

    return OutputSalesItem.from(salesItem);
  }

  updateSalesItem(id: string, inputSalesItem: InputSalesItem): void {
    if (!this.salesItemRepository.find(id)) {
      throw new EntityNotFoundError('Sales item', id);
    }

    const salesItem = SalesItem.from(inputSalesItem);
    this.salesItemRepository.update(id, salesItem);
  }

  deleteSalesItem(id: string): void {
    this.salesItemRepository.delete(id);
  }
}
