import { Repository } from 'typeorm';

import SalesItemRepository from '../SalesItemRepository';
import SalesItem from '../../entities/SalesItem';
import DbSalesItem from './entities/DbSalesItem';
import DatabaseError from '../../errors/DatabaseError';
import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';

@Injectable()
export default class TypeOrmSalesItemRepository implements SalesItemRepository {
  constructor(
    @InjectRepository(DbSalesItem)
    private repository: Repository<DbSalesItem>,
  ) {}

  async save(salesItem: SalesItem): Promise<void> {
    try {
      const dbSalesItem = DbSalesItem.from(salesItem);
      this.repository.save(dbSalesItem);
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async findAll(): Promise<SalesItem[]> {
    try {
      const dbSalesItems = await this.repository.find();
      return dbSalesItems.map((item) => item.toDomainEntity());
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async find(id: string): Promise<SalesItem | null> {
    try {
      const dbSalesItem = await this.repository.findOneBy({ id });
      return dbSalesItem ? dbSalesItem.toDomainEntity() : null;
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async update(salesItem: SalesItem): Promise<void> {
    try {
      const dbSalesItem = DbSalesItem.from(salesItem);
      await this.repository.save(dbSalesItem);
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async delete(id: string): Promise<void> {
    try {
      await this.repository.delete(id);
    } catch (error) {
      throw new DatabaseError(error);
    }
  }
}
