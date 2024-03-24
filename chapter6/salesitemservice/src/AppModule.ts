import { Module } from '@nestjs/common';
import RestSalesItemController from './controllers/RestSalesItemController';
import SalesItemServiceImpl from './services/SalesItemServiceImpl';
import ParamSqlSalesItemRepository from './repositories/ParamSqlSalesItemRepository';

@Module({
  imports: [],
  controllers: [RestSalesItemController],
  providers: [
    {
      provide: 'salesItemService',
      useClass: SalesItemServiceImpl,
    },
    {
      provide: 'salesItemRepository',
      useClass: ParamSqlSalesItemRepository,
    },
  ],
})
export class AppModule {}
