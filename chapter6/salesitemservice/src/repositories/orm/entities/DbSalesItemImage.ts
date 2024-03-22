import { Column, Entity, ManyToOne, PrimaryColumn } from 'typeorm';
import DbSalesItem from './DbSalesItem';

@Entity()
export default class DbSalesItemImage {
  @PrimaryColumn()
  id: string;

  @Column()
  salesItemId: number;

  @Column()
  rank: number;

  @Column()
  url: string;

  @ManyToOne(() => DbSalesItem, (dbSalesItem) => dbSalesItem.images, {
    cascade: true,
    eager: true,
  })
  dbSalesItem: DbSalesItem;
}
