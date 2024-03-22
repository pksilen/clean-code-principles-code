import { v4 as uuidv4 } from 'uuid';
import InputSalesItem from '../dtos/InputSalesItem';
import SalesItemImage from './SalesItemImage';

type Args = {
  id: string;
  createdAtTimestampInMs: number;
  name: string;
  priceInCents: number;
  images: SalesItemImage[];
};

export default class SalesItem {
  private _id: string;
  private _createdAtTimestampInMs: number;
  private _name: string;
  private _priceInCents: number;
  private _images: SalesItemImage[];

  constructor(args: Args) {
    this._id = args.id;
    this._createdAtTimestampInMs = args.createdAtTimestampInMs;
    this._name = args.name;
    this._priceInCents = args.priceInCents;
    this._images = args.images;
  }

  static from(inputSalesItem: InputSalesItem, id?: string): SalesItem {
    return new SalesItem({
      ...inputSalesItem,
      id: id ?? uuidv4(),
      createdAtTimestampInMs: Math.round(Date.now()),
      images: inputSalesItem.images.map(
        (image: any) => new SalesItemImage(image),
      ),
    });
  }

  get id(): string {
    return this._id;
  }

  get createdAtTimestampInMs(): number {
    return this._createdAtTimestampInMs;
  }

  get name(): string {
    return this._name;
  }

  get priceInCents(): number {
    return this._priceInCents;
  }

  get images(): SalesItemImage[] {
    return this._images;
  }
}
