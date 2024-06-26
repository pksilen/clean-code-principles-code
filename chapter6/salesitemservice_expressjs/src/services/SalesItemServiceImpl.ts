import SalesItemService from "./SalesItemService";
import InputSalesItem from "../dtos/InputSalesItem";
import OutputSalesItem from "../dtos/OutputSalesItem";
import SalesItem from "../entities/SalesItem";
import SalesItemRepository from "../repositories/SalesItemRepository";
import EntityNotFoundError from "../errors/EntityNotFoundError";
import { SortBy } from "../dtos/SalesItemsQuery";

export default class SalesItemServiceImpl implements SalesItemService {
  constructor(private readonly salesItemRepository: SalesItemRepository) {}

  async createSalesItem(
    inputSalesItem: InputSalesItem,
  ): Promise<OutputSalesItem> {
    const salesItem = SalesItem.from(inputSalesItem);
    await this.salesItemRepository.save(salesItem);
    return OutputSalesItem.from(salesItem);
  }

  async getSalesItems(
    search: string | undefined,
    page: number,
    sortBy: SortBy,
  ): Promise<OutputSalesItem[]> {
    const salesItems = await this.salesItemRepository.findAll(
      search,
      page,
      sortBy,
    );
    return await Promise.all(
      salesItems.map(
        async (salesItem) => await OutputSalesItem.from(salesItem),
      ),
    );
  }

  async getSalesItem(id: string): Promise<OutputSalesItem> {
    const salesItem = await this.salesItemRepository.find(id);

    if (!salesItem) {
      throw new EntityNotFoundError("Sales item", id);
    }

    return await OutputSalesItem.from(salesItem);
  }

  async updateSalesItem(
    id: string,
    inputSalesItem: InputSalesItem,
  ): Promise<void> {
    if (!(await this.salesItemRepository.find(id))) {
      throw new EntityNotFoundError("Sales item", id);
    }

    const salesItem = SalesItem.from(inputSalesItem, id);
    return this.salesItemRepository.update(salesItem);
  }

  async deleteSalesItem(id: string): Promise<void> {
    if (await this.salesItemRepository.find(id)) {
      await this.salesItemRepository.delete(id);
    }
  }
}
