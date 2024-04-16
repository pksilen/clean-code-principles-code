import { IsEnum, IsInt, IsOptional, MaxLength, Min } from "class-validator";

export enum SortBy {
  newest = "newest",
}

export class SalesItemsQuery {
  @IsOptional()
  @MaxLength(64)
  nameContains?: string;

  @IsInt()
  @Min(1)
  page: number;

  @IsEnum(SortBy)
  sortBy: SortBy;
}
