import * as mongodb from 'mongodb';
import SalesItemRepository from './SalesItemRepository';
import SalesItem from '../entities/SalesItem';
import DatabaseError from '../errors/DatabaseError';

export default class MongoDbSalesItemRepository implements SalesItemRepository {
  private readonly client: mongodb.MongoClient;
  private salesItemsCollection: mongodb.Collection | undefined;

  constructor() {
    try {
      const databaseUrl = process.env.DATABASE_URL ?? '';
      this.client = new mongodb.MongoClient(databaseUrl);
      const databaseName = databaseUrl.split('/')[3];

      this.client.connect().then(() => {
        const db = this.client.db(databaseName);
        this.salesItemsCollection = db.collection('salesitems');
      });
    } catch (error) {
      // Handle error
    }
  }

  async save(salesItem: SalesItem): Promise<void> {
    if (!this.salesItemsCollection) {
      throw new DatabaseError(new Error('Not ready'));
    }

    try {
      const salesItemDocument =
        MongoDbSalesItemRepository.toDocument(salesItem);

      await this.salesItemsCollection.insertOne(salesItemDocument);
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async findAll(): Promise<SalesItem[]> {
    if (!this.salesItemsCollection) {
      throw new DatabaseError(new Error('Not ready'));
    }

    try {
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
    if (!this.salesItemsCollection) {
      throw new DatabaseError(new Error('Not ready'));
    }

    try {
      const salesItemDocument = await this.salesItemsCollection.findOne({
        _id: new mongodb.ObjectId(id),
      });

      return salesItemDocument ? this.toDomainEntity(salesItemDocument) : null;
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  async update(salesItem: SalesItem): Promise<void> {
    if (!this.salesItemsCollection) {
      throw new DatabaseError(new Error('Not ready'));
    }

    try {
      const filter = { _id: new mongodb.ObjectId(salesItem.id) };

      const update = {
        $set: MongoDbSalesItemRepository.toDocumentWithout(salesItem, [
          '_id',
          'createdAtTimestampInMs',
        ]),
      };

      await this.salesItemsCollection.updateOne(filter, update);
    } catch (error) {
      throw new DatabaseError(error.message);
    }
  }

  async delete(id: string): Promise<void> {
    if (!this.salesItemsCollection) {
      throw new DatabaseError(new Error('Not ready'));
    }

    try {
      await this.salesItemsCollection.deleteOne({
        _id: new mongodb.ObjectId(id),
      });
    } catch (error) {
      throw new DatabaseError(error);
    }
  }

  private toDomainEntity(salesItemDoc: mongodb.Document): SalesItem {
    const { _id, ...rest } = salesItemDoc;
    return { id: _id.toString(), ...rest } as SalesItem;
  }

  static toDocument(salesItem: SalesItem): Record<string, any> {
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

  static toDocumentWithout(
    salesItem: SalesItem,
    keys: string[],
  ): Record<string, any> {
    const fullDocument = MongoDbSalesItemRepository.toDocument(salesItem);

    return Object.fromEntries(
      Object.entries(fullDocument).filter(([key]) => !keys.includes(key)),
    );
  }
}
