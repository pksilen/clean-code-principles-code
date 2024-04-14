import SalesItemRepository from '../../SalesItemRepository';
import SalesItem from '../../../entities/SalesItem';
import DatabaseError from '../../../errors/DatabaseError';
import { Injectable } from '@nestjs/common';
import { Prisma, PrismaClient } from '@prisma/client';
import { SortBy } from '../../../dtos/SalesItemsQuery';
import Constants from '../../../common/Constants';

@Injectable()
export default class PrismaOrmSalesItemRepository
  implements SalesItemRepository
{
  private readonly prismaClient: PrismaClient;

  constructor() {
    this.prismaClient = new PrismaClient();
  }

  async save(salesItem: SalesItem): Promise<void> {
    try {
      const data = PrismaOrmSalesItemRepository.toData(salesItem);
      await this.prismaClient.salesItem.create({ data });
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
      const dbSalesItems = await this.prismaClient.salesItem.findMany({
        include: { images: true },
        where: search ? { name: { contains: search } } : {},
        orderBy: [PrismaOrmSalesItemRepository.createOrderObject(sortBy)],
        skip: (page - 1) * Constants.PAGE_SIZE,
        take: Constants.PAGE_SIZE,
      });

      return dbSalesItems.map((item) =>
        PrismaOrmSalesItemRepository.toDomainEntity(item),
      );
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async find(id: string): Promise<SalesItem | null> {
    try {
      const dbSalesItem = await this.prismaClient.salesItem.findUnique({
        where: {
          id,
        },
        include: { images: true },
      });

      return dbSalesItem
        ? PrismaOrmSalesItemRepository.toDomainEntity(dbSalesItem)
        : null;
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async update(salesItem: SalesItem): Promise<void> {
    try {
      const data = PrismaOrmSalesItemRepository.toData(salesItem);

      await this.prismaClient.$transaction([
        this.prismaClient.salesItem.delete({
          where: { id: salesItem.id },
        }),
        this.prismaClient.salesItem.create({ data }),
      ]);
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async delete(id: string): Promise<void> {
    try {
      await this.prismaClient.salesItem.delete({
        where: { id },
      });
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  private static createOrderObject(
    sortBy: SortBy,
  ): Prisma.SalesItemOrderByWithRelationInput {
    switch (sortBy) {
      case 'newest':
        return { createdAtTimestampInMs: 'desc' };
    }

    return {
      name: 'asc',
    };
  }

  private static toData(salesItem: SalesItem) {
    return {
      id: salesItem.id,
      createdAtTimestampInMs: salesItem.createdAtTimestampInMs,
      name: salesItem.name,
      priceInCents: salesItem.priceInCents,
      images: {
        create: salesItem.images.map((image) => ({
          id: image.id,
          rank: image.rank,
          url: image.url,
        })),
      },
    };
  }

  private static toDomainEntity(salesItemObject: any): SalesItem {
    return SalesItem.from(salesItemObject, salesItemObject.id);
  }
}
