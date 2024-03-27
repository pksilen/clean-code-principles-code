import { IsInt, IsPositive, IsUrl, MaxLength } from 'class-validator';
import { Field, Int, ObjectType } from '@nestjs/graphql';

@ObjectType()
export default class OutputSalesItemImage {
  @Field()
  @MaxLength(36)
  id: string;

  @Field(() => Int)
  @IsInt()
  @IsPositive()
  rank: number;

  @Field()
  @IsUrl()
  url: string;
}
