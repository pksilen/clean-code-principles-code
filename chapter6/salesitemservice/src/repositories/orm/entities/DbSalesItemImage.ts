import { Column, Entity } from 'typeorm';

@Entity()
export default class DbSalesItemImage {
  @Column()
  id: number;

  @Column()
  salesItemId: number;

  @Column()
  rank: number;

  @Column()
  url: string;
}
