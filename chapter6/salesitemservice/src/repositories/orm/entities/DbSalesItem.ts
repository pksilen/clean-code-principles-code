import { Column, Entity, OneToMany, PrimaryColumn } from 'typeorm';
import DbSalesItemImage from './DbSalesItemImage';
import SalesItem from '../../../entities/SalesItem';

@Entity()
export default class DbSalesItem {
  @PrimaryColumn()
  id: string;

  @Column()
  createdAtTimestampInMs: number;

  @Column()
  name: string;

  @Column()
  priceInCents: number;

  @OneToMany(
    () => DbSalesItemImage,
    (dbSalesItemImage) => dbSalesItemImage.dbSalesItem,
  )
  images: DbSalesItemImage[];

  static from(salesItem: SalesItem): DbSalesItem {
    const dbSalesItem = new DbSalesItem();
    dbSalesItem.id = salesItem.id;
    dbSalesItem.createdAtTimestampInMs = salesItem.createdAtTimestampInMs;
    dbSalesItem.name = salesItem.name;
    dbSalesItem.priceInCents = salesItem.priceInCents;

    dbSalesItem.images = salesItem.images.map((salesItemImage) => {
      const dbSalesItemImage = new DbSalesItemImage();
      dbSalesItemImage.id = salesItemImage.id;
      dbSalesItemImage.rank = salesItemImage.rank;
      dbSalesItemImage.url = salesItemImage.url;
      return dbSalesItemImage;
    });

    return dbSalesItem;
  }

  toDomainEntity(): SalesItem {
    return SalesItem.from(this);
  }
}
