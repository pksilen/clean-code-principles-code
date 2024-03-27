import { Field, ObjectType } from '@nestjs/graphql';

@ObjectType()
export default class IdResponse {
  @Field()
  id: string;
}
