import { Column, Entity, ManyToOne, PrimaryColumn } from 'typeorm';
import DbSalesItem from './DbSalesItem';

@Entity('salesitemimages')
export default class DbSalesItemImage {
  @PrimaryColumn()
  id: string;

  @Column()
  salesItemId: number;

  @Column()
  rank: number;

  @Column()
  url: string;

  @ManyToOne(() => DbSalesItem, (salesItem) => salesItem.images, {
    onDelete: 'CASCADE',
  })
  salesItem: DbSalesItem;
}
