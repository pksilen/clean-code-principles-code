import * as mongodb from 'mongodb';
import SalesItemRepository from './SalesItemRepository';
import SalesItem from '../entities/SalesItem';
import DatabaseError from '../errors/DatabaseError';

export default class MongoDbSalesItemRepository implements SalesItemRepository {
  private readonly client: mongodb.MongoClient;
  private salesItemsCollection: mongodb.Collection;

  constructor() {
    try {
      const databaseUrl = process.env.DATABASE_URL ?? '';
      this.client = new mongodb.MongoClient(databaseUrl);
    } catch (error) {
      // Handle error
    }
  }

  async save(salesItem: SalesItem): Promise<void> {
    const salesItemDocument = MongoDbSalesItemRepository.toDocument(salesItem);

    try {
      await this.connectIfNeeded();
      await this.salesItemsCollection.insertOne(salesItemDocument);
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async findAll(): Promise<SalesItem[]> {
    try {
      await this.connectIfNeeded();
      const cursor = this.salesItemsCollection.find();
      const salesItemDocuments = await cursor.toArray();

      return salesItemDocuments.map((salesItemDocument) =>
        this.toDomainEntity(salesItemDocument),
      );
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async find(id: string): Promise<SalesItem | null> {
    try {
      await this.connectIfNeeded();

      const salesItemDocument = await this.salesItemsCollection.findOne({
        _id: id as any,
      });

      return salesItemDocument ? this.toDomainEntity(salesItemDocument) : null;
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async update(salesItem: SalesItem): Promise<void> {
    const filter = { _id: salesItem.id as any };

    const update = {
      $set: MongoDbSalesItemRepository.toDocumentWithout(salesItem, [
        '_id',
        'createdAtTimestampInMs',
      ]),
    };

    try {
      await this.connectIfNeeded();
      await this.salesItemsCollection.updateOne(filter, update);
    } catch (error) {
      throw new DatabaseError(error.message);
    }
  }

  async delete(id: string): Promise<void> {
    try {
      await this.connectIfNeeded();

      await this.salesItemsCollection.deleteOne({
        _id: id as any,
      });
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  private async connectIfNeeded() {
    if (this.salesItemsCollection) {
      return;
    }

    await this.client.connect();
    const databaseUrl = process.env.DATABASE_URL ?? '';
    const databaseName = databaseUrl.split('/')[3];
    const db = this.client.db(databaseName);
    this.salesItemsCollection = db.collection('salesitems');
  }

  private toDomainEntity(salesItemDoc: mongodb.Document): SalesItem {
    const { _id, ...rest } = salesItemDoc;
    return { id: _id.toString(), ...rest } as SalesItem;
  }

  private static toDocument(salesItem: SalesItem): Record<string, any> {
    return {
      _id: salesItem.id,
      createdAtTimestampInMs: salesItem.createdAtTimestampInMs,
      name: salesItem.name,
      priceInCents: salesItem.priceInCents,
      images: salesItem.images.map((image) => ({
        id: image.id,
        rank: image.rank,
        url: image.url,
      })),
    };
  }

  private static toDocumentWithout(
    salesItem: SalesItem,
    keys: string[],
  ): Record<string, any> {
    const fullDocument = MongoDbSalesItemRepository.toDocument(salesItem);

    return Object.fromEntries(
      Object.entries(fullDocument).filter(([key]) => !keys.includes(key)),
    );
  }
}
