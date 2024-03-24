import InputSalesItem from './InputSalesItem';
import {
  ArrayMaxSize,
  MaxLength,
  ValidateNested,
  validateOrReject,
} from 'class-validator';
import SalesItem from '../entities/SalesItem';
import OutputSalesItemImage from './OutputSalesItemImage';

export default class OutputSalesItem extends InputSalesItem {
  @MaxLength(36)
  id: string;

  @MaxLength(20)
  createdAtTimestampInMs: string;

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
