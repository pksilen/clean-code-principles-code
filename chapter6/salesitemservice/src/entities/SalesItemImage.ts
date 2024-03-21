import { v4 as uuidv4 } from 'uuid';

type Args = {
  id: string;
  rank: number;
  url: string;
};

export default class SalesItemImage {
  private _id: string;
  private _rank: number;
  private _url: string;

  constructor(args: Args) {
    this._id = args.id ?? uuidv4();
    this._rank = args.rank;
    this._url = args.url;
  }

  get id(): string {
    return this._id;
  }

  get rank(): number {
    return this._rank;
  }

  get url(): string {
    return this._url;
  }
}
