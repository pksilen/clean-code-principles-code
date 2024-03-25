import { IsInt, IsPositive, IsUrl } from 'class-validator';

export default class InputSalesItemImage {
  @IsInt()
  @IsPositive()
  rank: number;

  @IsUrl()
  url: string;
}
