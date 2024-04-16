import "reflect-metadata";
import * as bodyParser from "body-parser";
import * as express from "express";
import salesItemController from "./controllers/salesItemController";
import validationErrorHandler from "./errorhandlers/validationErrorHandler";
import genericErrorHandler from "./errorhandlers/genericErrorHandler";
import salesItemServiceErrorHandler from "./errorhandlers/salesItemServiceErrorHandler";
import requestCountingMiddleware from "./middleware/requestCountingMiddleware";
import requestTracingMiddleware from "./middleware/requestTracingMiddleware";

const app = express();
app.use(bodyParser.json());

app.use(requestCountingMiddleware);
app.use(requestTracingMiddleware);

app.use("/sales-items", salesItemController);

app.use(salesItemServiceErrorHandler);
app.use(validationErrorHandler);
app.use(genericErrorHandler);

app.listen(8000);
