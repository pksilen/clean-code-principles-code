const PROTO_PATH = __dirname + '/controllers/grpc/sales_item_service.proto';
import { credentials, loadPackageDefinition } from '@grpc/grpc-js';
import { loadSync } from '@grpc/proto-loader';

const packageDefinition = loadSync(PROTO_PATH, {
  keepCase: true,
  longs: String,
  enums: String,
  defaults: true,
  oneofs: true,
});

const salesitemservice =
  loadPackageDefinition(packageDefinition).salesitemservice;

const grpcClient = new (salesitemservice as any).SalesItemService(
  'localhost:50051',
  credentials.createInsecure(),
);

grpcClient.getSalesItems({}, (error, response) => {
  console.log(error, JSON.stringify(response.salesItems, undefined, 2));
});

/* grpcClient.getSalesItem(
  { id: '94d24e8d-2ae3-49b8-844a-29e3031e5d60' },
  (error, response) => {
    console.log(error, JSON.stringify(response, undefined, 2));
  },
); */

grpcClient.createSalesItem(
  {
    name: 'Sales item 11',
    priceInCents: 2000,
    images: [{ rank: 1, url: 'http://test.com/images/1' }],
  },
  (error, response) => {
    console.log(error, JSON.stringify(response, undefined, 2));
  },
);

grpcClient.updateSalesItem(
  {
    id: 'b8f691e3-32e4-4971-9a27-8e9b269724f1',
    name: 'Sales item 22',
    priceInCents: 3000,
    images: [{ rank: 2222, url: 'http://test.com/images/1' }],
  },
  (error, response) => {
    console.log(error, response);
  },
);

/* grpcClient.deleteSalesItem(
  { id: '94d24e8d-2ae3-49b8-844a-29e3031e5d60' },
  (error, response) => {
    console.log(error, response);
  },
); */
