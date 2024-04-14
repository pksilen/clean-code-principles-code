import { IsEnum, IsInt, IsOptional, MaxLength, Min } from 'class-validator';
import { Field, InputType } from '@nestjs/graphql';

export enum SortBy {
  newest = 'newest',
}

@InputType()
export class SalesItemsQuery {
  @Field()
  @IsOptional()
  @MaxLength(64)
  nameContains?: string;

  @Field()
  @IsInt()
  @Min(1)
  page: number;

  @Field()
  @IsEnum(SortBy)
  sortBy: SortBy;
}
