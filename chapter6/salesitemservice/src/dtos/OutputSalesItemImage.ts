import InputSalesItemImage from './InputSalesItemImage';
import { MaxLength } from 'class-validator';

export default class OutputSalesItemImage extends InputSalesItemImage {
  @MaxLength(36)
  id: string;
}
