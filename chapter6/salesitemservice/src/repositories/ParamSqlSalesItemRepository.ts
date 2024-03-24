import mysql, { Pool } from 'mysql2/promise';
import SalesItemRepository from './SalesItemRepository';
import SalesItem from '../entities/SalesItem';
import DatabaseError from 'src/errors/DatabaseError';
import SalesItemImage from '../entities/SalesItemImage';

interface DatabaseConfig {
  user: string;
  password: string;
  host: string;
  port: number; // Assuming the port is a number
  database: string;
  poolName?: string; // Optional
  poolSize?: number; // Optional
}

class ParamSqlSalesItemRepository implements SalesItemRepository {
  private readonly connectionPool: Pool;

  constructor() {
    const connConfig = ParamSqlSalesItemRepository.tryCreateConnConfig();
    this.connectionPool = mysql.createPool(connConfig);
    this.tryCreateDbTablesIfNeeded();
  }

  async save(salesItem: SalesItem): Promise<void> {
    let connection: mysql.PoolConnection | undefined;

    try {
      connection = await this.connectionPool.getConnection();

      await connection.execute(
        'INSERT INTO salesitems (id, createdAtTimestampInMs, name, priceInCents) VALUES (?, ?, ?, ?)',
        [
          salesItem.id,
          salesItem.createdAtTimestampInMs,
          salesItem.name,
          salesItem.priceInCents,
        ],
      );

      await this.insertSalesItemImages(
        connection,
        salesItem.id,
        salesItem.images,
      );

      await connection.commit();
    } catch (error) {
      throw new DatabaseError(error);
    } finally {
      connection?.release();
    }
  }

  async findAll(): Promise<SalesItem[]> {
    let connection: mysql.PoolConnection | undefined;

    try {
      connection = await this.connectionPool.getConnection();
      const [rows] = await connection.execute(
        'SELECT s.id, s.createdAtTimestampInMs, s.name, s.priceInCents, si.id, si.rank, si.url ' +
          'FROM salesitems s LEFT JOIN salesitemimages si ON si.salesItemId = s.id',
      );

      return this.getSalesItems(rows as any[]);
    } catch (error) {
      throw new DatabaseError(error);
    } finally {
      connection?.release();
    }
  }

  async find(id: string): Promise<SalesItem | null> {
    let connection: mysql.PoolConnection | undefined;

    try {
      connection = await this.connectionPool.getConnection();

      const [rows] = await connection.execute(
        'SELECT s.id, s.createdAtTimestampInMs, s.name, s.priceInCents, si.id, si.rank, si.url ' +
          'FROM salesitems s LEFT JOIN salesitemimages si ON si.salesItemId = s.id WHERE s.id = ?',
        [id],
      );

      return this.getSalesItems(rows as any[])[0] || null;
    } catch (error) {
      throw new DatabaseError(error);
    } finally {
      connection?.release();
    }
  }

  async update(salesItem: SalesItem): Promise<void> {
    let connection: mysql.PoolConnection | undefined;

    try {
      connection = await this.connectionPool.getConnection();

      await connection.execute(
        'UPDATE salesitems SET name = ?, priceInCents = ? WHERE id = ?',
        [salesItem.name, salesItem.priceInCents, salesItem.id],
      );

      await connection.execute(
        'DELETE FROM salesitemimages WHERE salesItemId = ?',
        [salesItem.id],
      );

      await this.insertSalesItemImages(
        connection,
        salesItem.id,
        salesItem.images,
      );

      await connection.commit();
    } catch (error) {
      throw new DatabaseError(error);
    } finally {
      connection?.release();
    }
  }

  async delete(id: string): Promise<void> {
    let connection: mysql.PoolConnection | undefined;

    try {
      connection = await this.connectionPool.getConnection();

      await connection.execute(
        'DELETE FROM salesitemimages WHERE salesItemId = ?',
        [id],
      );

      await connection.execute('DELETE FROM salesitems WHERE id = ?', [id]);
      await connection.commit();
    } catch (error) {
      throw new DatabaseError(error);
    } finally {
      connection?.release();
    }
  }

  private static tryCreateConnConfig(): DatabaseConfig {
    const databaseUrl = process.env.DATABASE_URL;

    if (!databaseUrl) {
      throw new Error('DATABASE_URL environment variable is not set');
    }

    const [, authAndHost, path] = databaseUrl.split('/');
    const [userAndPassword, hostAndPort] = authAndHost.split('@');
    const [user, password] = userAndPassword.split(':');
    const [host, portString] = hostAndPort.split(':');
    const port = parseInt(portString, 10);
    const database = path.slice(1);

    return {
      user,
      password,
      host,
      port,
      database,
      poolName: 'salesitems',
      poolSize: 25,
    };
  }

  async tryCreateDbTablesIfNeeded(): Promise<void> {
    const connection = await this.connectionPool.getConnection();

    try {
      const createSalesItemsTableQuery = `
                CREATE TABLE IF NOT EXISTS salesitems (
                    id VARCHAR(36) NOT NULL,
                    createdAtTimestampInMs INTEGER NOT NULL,
                    name VARCHAR(256) NOT NULL,
                    priceInCents INTEGER NOT NULL,
                    PRIMARY KEY (id)
                )
            `;

      await connection.execute(createSalesItemsTableQuery);

      const createSalesItemImagesTableQuery = `
                CREATE TABLE IF NOT EXISTS salesitemimages (
                    id VARCHAR(36) NOT NULL,
                    \`rank\` INTEGER NOT NULL,
                    url VARCHAR(2084) NOT NULL,
                    salesItemId VARCHAR(36) NOT NULL,
                    PRIMARY KEY (id),
                    FOREIGN KEY (salesItemId) REFERENCES salesitems(id)
                )
            `;

      await connection.execute(createSalesItemImagesTableQuery);
      await connection.commit();
    } catch (error) {
      throw new Error(`Error creating tables: ${error.message}`);
    } finally {
      connection.release();
    }
  }

  private async insertSalesItemImages(
    connection: mysql.Connection,
    salesItemId: string,
    images: SalesItemImage[],
  ) {
    const statement =
      'INSERT INTO salesitemimages (id, `rank`, url, salesItemId) VALUES (?, ?, ?, ?)';

    for (const image of images) {
      await connection.execute(statement, [
        image.id,
        image.rank,
        image.url,
        salesItemId,
      ]);
    }
  }

  private getSalesItems(cursor: any): SalesItem[] {
    const idToSalesItem: Record<string, SalesItem> = {};

    for (const row of cursor) {
      if (!idToSalesItem[row.id]) {
        idToSalesItem[row.id] = SalesItem.from(row);
      }

      if (row.imageId) {
        idToSalesItem[row.id].images.push(
          new SalesItemImage({
            id: row.imageId,
            rank: row.imageRank,
            url: row.imageUrl,
          }),
        );
      }
    }
    return Object.values(idToSalesItem);
  }
}
