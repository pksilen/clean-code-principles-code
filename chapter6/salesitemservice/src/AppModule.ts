import { Module } from '@nestjs/common';
import RestSalesItemController from './controllers/RestSalesItemController';
import SalesItemServiceImpl from './services/SalesItemServiceImpl';
import TypeOrmSalesItemRepository from './repositories/orm/TypeOrmSalesItemRepository';

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
      useClass: TypeOrmSalesItemRepository,
    },
  ],
})
export class AppModule {}
