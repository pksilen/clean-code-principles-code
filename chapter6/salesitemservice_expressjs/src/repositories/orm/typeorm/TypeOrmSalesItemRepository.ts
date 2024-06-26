import SalesItemRepository from "../../SalesItemRepository";
import SalesItem from "../../../entities/SalesItem";
import DbSalesItem from "./entities/DbSalesItem";
import DatabaseError from "../../../errors/DatabaseError";
import { DataSource, ILike } from "typeorm";
import DbSalesItemImage from "./entities/DbSalesItemImage";
import { getDbConnProperties } from "../../../common/utils/utils";
import { SortBy } from "../../../dtos/SalesItemsQuery";
import Constants from "../../../common/Constants";

export default class TypeOrmSalesItemRepository implements SalesItemRepository {
  private readonly dataSource: DataSource;
  private isDataSourceInitialized = false;

  constructor() {
    const { user, password, host, port, database } = getDbConnProperties();

    this.dataSource = new DataSource({
      type: "mysql",
      host: host,
      port: port,
      username: user,
      password: password,
      database: database,
      entities: [DbSalesItem, DbSalesItemImage],
      // Do not use 'true' in production environment,
      // but use migrations when needed
      synchronize: true,
    });
  }

  async save(salesItem: SalesItem): Promise<void> {
    try {
      await this.initializeDataSourceIfNeeded();
      const dbSalesItem = DbSalesItem.from(salesItem);
      await this.dataSource.manager.save(dbSalesItem);
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async findAll(
    search: string | undefined,
    page: number,
    sortBy: SortBy,
  ): Promise<SalesItem[]> {
    try {
      await this.initializeDataSourceIfNeeded();

      const dbSalesItems = await this.dataSource.manager.find(DbSalesItem, {
        where: search
          ? {
              name: ILike(`%${search}%`),
            }
          : {},
        order: TypeOrmSalesItemRepository.createOrderObject(sortBy),
        take: Constants.PAGE_SIZE,
        skip: (page - 1) * Constants.PAGE_SIZE,
      });

      return dbSalesItems.map((item) => item.toDomainEntity());
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async find(id: string): Promise<SalesItem | null> {
    try {
      await this.initializeDataSourceIfNeeded();
      const dbSalesItem = await this.dataSource.manager.findOneBy(DbSalesItem, {
        id,
      });

      return dbSalesItem ? dbSalesItem.toDomainEntity() : null;
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async update(salesItem: SalesItem): Promise<void> {
    try {
      await this.initializeDataSourceIfNeeded();
      const dbSalesItem = DbSalesItem.from(salesItem);

      await this.dataSource.transaction(async (transactionalEntityManager) => {
        await transactionalEntityManager.delete(DbSalesItem, salesItem.id);
        await transactionalEntityManager.save(dbSalesItem);
      });
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async delete(id: string): Promise<void> {
    try {
      await this.initializeDataSourceIfNeeded();
      await this.dataSource.manager.delete(DbSalesItem, id);
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  private async initializeDataSourceIfNeeded() {
    if (!this.isDataSourceInitialized) {
      await this.dataSource.initialize();
      this.isDataSourceInitialized = true;
    }
  }

  private static createOrderObject(
    sortBy: SortBy,
  ): Partial<
    Omit<Record<keyof DbSalesItem, "ASC" | "DESC">, "toDomainEntity">
  > {
    switch (sortBy) {
      case "newest":
        return { createdAtTimestampInMs: "DESC" };
    }

    return {
      name: "ASC",
    };
  }
}
