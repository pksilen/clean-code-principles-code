import { Module } from '@nestjs/common';
import RestSalesItemController from './controllers/RestSalesItemController';
import SalesItemServiceImpl from './services/SalesItemServiceImpl';
import TypeOrmSalesItemRepository from './repositories/orm/typeorm/TypeOrmSalesItemRepository';
import * as process from 'process';
import ParamSqlSalesItemRepository from './repositories/ParamSqlSalesItemRepository';
import MongoDbSalesItemRepository from './repositories/MongoDbSalesItemRepository';
import PrismaOrmSalesItemRepository from './repositories/orm/prisma/PrismaOrmSalesItemRepository';
import { GraphQLModule } from '@nestjs/graphql';
import { ApolloDriver, ApolloDriverConfig } from '@nestjs/apollo';
import { ApolloServerPluginLandingPageLocalDefault } from '@apollo/server/plugin/landingPage/default';
import GraphQlSalesItemController from './controllers/graphql/GraphQlSalesItemController';

function getSalesItemRepositoryClass() {
  if (process.env.DATABASE_URL?.startsWith('mongodb')) {
    return MongoDbSalesItemRepository;
  } else if (process.env.REPOSITORY_TYPE === 'typeorm') {
    return TypeOrmSalesItemRepository;
  } else if (process.env.REPOSITORY_TYPE === 'prismaorm') {
    return PrismaOrmSalesItemRepository;
  } else if (process.env.REPOSITORY_TYPE === 'paramsql') {
    return ParamSqlSalesItemRepository;
  }

  return TypeOrmSalesItemRepository;
}

@Module({
  imports: [
    GraphQLModule.forRoot<ApolloDriverConfig>({
      driver: ApolloDriver,
      autoSchemaFile: true,
      playground: false,
      plugins: [ApolloServerPluginLandingPageLocalDefault()],
    }),
  ],
  controllers: [RestSalesItemController],
  providers: [
    GraphQlSalesItemController,
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
