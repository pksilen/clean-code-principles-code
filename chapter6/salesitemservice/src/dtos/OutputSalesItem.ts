import {
  ArrayMaxSize,
  IsInt,
  MaxLength,
  ValidateNested,
  validateOrReject,
} from 'class-validator';
import SalesItem from '../entities/SalesItem';
import OutputSalesItemImage from './OutputSalesItemImage';
import { Field, Int, ObjectType } from '@nestjs/graphql';

@ObjectType()
export default class OutputSalesItem {
  @Field()
  @MaxLength(36)
  id: string;

  @Field()
  @MaxLength(20)
  createdAtTimestampInMs: string;

  @Field()
  @MaxLength(256)
  name: string;

  // We accept negative prices for sales items that act
  // as discount items
  @Field(() => Int)
  @IsInt()
  priceInCents: number;

  @Field(() => [OutputSalesItemImage])
  @ValidateNested()
  @ArrayMaxSize(25)
  images: OutputSalesItemImage[];

  static async from(salesItem: SalesItem): Promise<OutputSalesItem> {
    const outputSalesItem = new OutputSalesItem();
    outputSalesItem.id = salesItem.id;
    outputSalesItem.createdAtTimestampInMs = salesItem.createdAtTimestampInMs;
    outputSalesItem.name = salesItem.name;
    outputSalesItem.priceInCents = salesItem.priceInCents;

    outputSalesItem.images = salesItem.images.map((salesItemImage) => {
      const outputSalesItemImage = new OutputSalesItemImage();
      outputSalesItemImage.id = salesItemImage.id;
      outputSalesItemImage.rank = salesItemImage.rank;
      outputSalesItemImage.url = salesItemImage.url;
      return outputSalesItemImage;
    });

    await validateOrReject(outputSalesItem);
    return outputSalesItem;
  }
}
