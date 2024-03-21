import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm';
import DbSalesItemImage from './DbSalesItemImage';

@Entity()
export default class DbSalesItem {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  name: string;

  @Column()
  priceInCents: number;

  @Column()
  images: DbSalesItemImage[];
}
