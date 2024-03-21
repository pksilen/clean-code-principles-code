import { Module } from '@nestjs/common';
import RestSalesItemController from './controllers/RestSalesItemController';

@Module({
  imports: [],
  controllers: [RestSalesItemController],
  providers: [],
})
export class AppModule {}
