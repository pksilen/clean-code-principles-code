import GrpcSalesItemController from './controllers/grpc/GrpcSalesItemController';
import {
  loadPackageDefinition,
  Server,
  ServerCredentials,
} from '@grpc/grpc-js';
import { loadSync } from '@grpc/proto-loader';
import SalesItemServiceImpl from './services/SalesItemServiceImpl';
import PrismaOrmSalesItemRepository from './repositories/orm/prisma/PrismaOrmSalesItemRepository';

const PROTO_PATH = __dirname + '/controllers/grpc/sales_item_service.proto';

const packageDefinition = loadSync(PROTO_PATH, {
  keepCase: true,
  longs: String,
  enums: String,
  defaults: true,
  oneofs: true,
});

const salesitemservice =
  loadPackageDefinition(packageDefinition).salesitemservice;

const salesItemRepository = new PrismaOrmSalesItemRepository();
const salesItemService = new SalesItemServiceImpl(salesItemRepository);
const grpcSalesItemController = new GrpcSalesItemController(salesItemService);
const grpcServer = new Server();

grpcServer.addService(
  (salesitemservice as any).SalesItemService.service,
  grpcSalesItemController.getRequestHandlers() as any,
);

grpcServer.bindAsync(
  '0.0.0.0:50051',
  ServerCredentials.createInsecure(),
  () => {
    // Handle error
  },
);
