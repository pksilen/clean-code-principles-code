import { Module } from '@nestjs/common';
import RestSalesItemController from './controllers/RestSalesItemController';
import SalesItemServiceImpl from './services/SalesItemServiceImpl';
import TypeOrmSalesItemRepository from './repositories/orm/TypeOrmSalesItemRepository';
import { TypeOrmModule } from '@nestjs/typeorm';
import DbSalesItem from './repositories/orm/entities/DbSalesItem';

@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: 'mysql',
      host: 'localhost',
      port: 3306,
      username: 'root',
      password: 'password',
      database: 'salesitemservice',
      entities: [DbSalesItem],
      // Setting synchronize: true shouldn't be used in production
      // because you can lose production data.
      synchronize: true,
    }),
  ],
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
