import { IsInt, IsPositive, IsUrl, MaxLength } from "class-validator";

export default class OutputSalesItemImage {
  @MaxLength(36)
  id: string;

  @IsInt()
  @IsPositive()
  rank: number;

  @IsUrl()
  url: string;
}
