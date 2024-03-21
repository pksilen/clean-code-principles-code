import * as mongodb from 'mongodb';
import SalesItemRepository from './SalesItemRepository';
import SalesItem from '../entities/SalesItem';
import DatabaseError from '../errors/DatabaseError';

class MongoDbSalesItemRepository implements SalesItemRepository {
  private client: mongodb.MongoClient;
  private db: mongodb.Db; // For database instance
  private salesItemsCollection: mongodb.Collection;

  constructor() {
    try {
      const databaseUrl = process.env.DATABASE_URL || '';
      this.client = new mongodb.MongoClient(databaseUrl);
      const databaseName = databaseUrl.split('/')[3];

      // Connect and fetch the relevant database and collection
      this.client.connect().then(() => {
        this.db = this.client.db(databaseName);
        this.salesItemsCollection = this.db.collection('salesitems');
      });
    } catch (error) {
      // Log error appropriately
      throw error;
    }
  }

  async save(salesItem: SalesItem): Promise<void> {
    try {
      const salesItemDoc = this.toDocument(salesItem);
      await this.salesItemsCollection.insertOne(salesItemDoc);
    } catch (error) {
      if (error instanceof mongodb.MongoError) {
        throw new DatabaseError(error.message);
      } else {
        throw error; // Rethrow other error types
      }
    }
  }

  async findAll(): Promise<SalesItem[]> {
    try {
      const cursor = this.salesItemsCollection.find();
      const salesItemDocs = await cursor.toArray();

      return salesItemDocs.map((doc) => this.toDomainEntity(doc));
    } catch (error) {
      // Same error handling as above
      throw new DatabaseError(error.message);
    }
  }

  async find(id: string): Promise<SalesItem | null> {
    try {
      const salesItemDoc = await this.salesItemsCollection.findOne({
        _id: new mongodb.ObjectId(id),
      });
      return salesItemDoc ? this.toDomainEntity(salesItemDoc) : null;
    } catch (error) {
      throw new DatabaseError(error.message);
    }
  }

  async update(salesItem: SalesItem): Promise<void> {
    try {
      const filter = { _id: new mongodb.ObjectId(salesItem.id) };
      const update = {
        $set: this.toDocumentWithout(salesItem, [
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
    // ... (Similar to other methods)
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
      Object.entries(fullDocument).filter(([key, _]) => !keys.includes(key)),
    );
  }
}
