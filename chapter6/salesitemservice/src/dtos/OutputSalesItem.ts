import InputSalesItem from './InputSalesItem';
import { IsInt, IsPositive, MaxLength } from 'class-validator';
import SalesItem from '../entities/SalesItem';
import { transformAndValidate } from 'class-transformer-validator';

export default class OutputSalesItem extends InputSalesItem {
  @MaxLength(256)
  id: string;

  @IsInt()
  @IsPositive()
  createdAtTimestampInMs: number;

  static async from(salesItem: SalesItem): Promise<OutputSalesItem> {
    return await transformAndValidate(OutputSalesItem, salesItem);
  }
}
