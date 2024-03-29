import {
  ArrayMaxSize,
  IsInt,
  MaxLength,
  ValidateNested,
} from "class-validator";
import InputSalesItemImage from "./InputSalesItemImage";
import { Type } from "class-transformer";

export default class InputSalesItem {
  @MaxLength(256)
  name: string;

  // We accept negative prices for sales items that act
  // as discount items
  @IsInt()
  priceInCents: number;

  @Type(() => InputSalesItemImage)
  @ValidateNested()
  @ArrayMaxSize(25)
  images: InputSalesItemImage[];
}
