import { Module } from '@nestjs/common';
import RestSalesItemController from './controllers/RestSalesItemController';
import SalesItemServiceImpl from './services/SalesItemServiceImpl';
import TypeOrmSalesItemRepository from './repositories/orm/TypeOrmSalesItemRepository';
import * as process from 'process';
import ParamSqlSalesItemRepository from './repositories/ParamSqlSalesItemRepository';
import MongoDbSalesItemRepository from './repositories/MongoDbSalesItemRepository';

function getSalesItemRepositoryClass() {
  if (process.env.DATABASE_URL?.startsWith('mongodb')) {
    return MongoDbSalesItemRepository;
  } else if (process.env.REPOSITORY_TYPE?.startsWith('orm')) {
    return TypeOrmSalesItemRepository;
  } else if (process.env.REPOSITORY_TYPE?.startsWith('paramsql')) {
    return ParamSqlSalesItemRepository;
  }

  return TypeOrmSalesItemRepository;
}

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
      useClass: getSalesItemRepositoryClass(),
    },
  ],
})
export class AppModule {}
