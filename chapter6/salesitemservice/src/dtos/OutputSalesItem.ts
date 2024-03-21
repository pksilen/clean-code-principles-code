import InputSalesItem from './InputSalesItem';
import { IsInt, IsPositive, MaxLength } from 'class-validator';

export default class OutputSalesItem extends InputSalesItem {
  @MaxLength(256)
  id: string;

  @IsInt()
  @IsPositive()
  createdAtTimestampInMs: number;
}
