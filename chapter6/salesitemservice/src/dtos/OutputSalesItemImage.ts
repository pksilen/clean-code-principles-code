import { IsInt, IsPositive } from 'class-validator';
import InputSalesItemImage from './InputSalesItemImage';

export default class OutputSalesItemImage extends InputSalesItemImage {
  @IsInt()
  @IsPositive()
  id: number;
}
