import { Field, InputType, Int } from '@nestjs/graphql';
import {
  ArrayMaxSize,
  IsInt,
  MaxLength,
  ValidateNested,
} from 'class-validator';
import InputSalesItemImage from './InputSalesItemImage';

@InputType()
export default class InputSalesItem {
  @Field()
  @MaxLength(256)
  name: string;

  // We accept negative prices for sales items that act
  // as discount items
  @Field(() => Int)
  @IsInt()
  priceInCents: number;

  @Field(() => [InputSalesItemImage])
  @ValidateNested()
  @ArrayMaxSize(25)
  images: InputSalesItemImage[];
}
