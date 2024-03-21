import { Module } from '@nestjs/common';
import { RestSalesItemController } from './RestSalesItemController';

@Module({
  imports: [],
  controllers: [RestSalesItemController],
  providers: [],
})
export class AppModule {}
