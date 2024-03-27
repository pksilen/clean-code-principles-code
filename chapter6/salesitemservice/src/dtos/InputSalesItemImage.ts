import { IsInt, IsPositive, IsUrl } from 'class-validator';
import { Field, InputType, Int } from '@nestjs/graphql';

@InputType()
export default class InputSalesItemImage {
  @Field(() => Int)
  @IsInt()
  @IsPositive()
  rank: number;

  @Field()
  @IsUrl()
  url: string;
}
