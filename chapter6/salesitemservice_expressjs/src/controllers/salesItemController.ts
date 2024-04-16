import { transformAndValidate } from "class-transformer-validator";
import * as express from "express";
import HttpStatus from "http-status-codes";
import SalesItemServiceImpl from "../services/SalesItemServiceImpl";
import ParamSqlSalesItemRepository from "../repositories/ParamSqlSalesItemRepository";
import InputSalesItem from "../dtos/InputSalesItem";
import { SalesItemsQuery } from "../dtos/SalesItemsQuery";
import auditLoggingMiddleware from "../middleware/auditLoggingMiddleware";

const salesItemRepository = new ParamSqlSalesItemRepository();
const salesItemService = new SalesItemServiceImpl(salesItemRepository);
const salesItemController = express.Router();

salesItemController.post("/", async (request, response, next) => {
  // From Express.js 5.x onwards, the try-catch block and calling next(error) is not needed
  try {
    const inputSalesItem = await transformAndValidate(
      InputSalesItem,
      request.body as object,
    );

    const outputSalesItem =
      await salesItemService.createSalesItem(inputSalesItem);

    response.status(HttpStatus.CREATED).send(outputSalesItem);
  } catch (error) {
    next(error);
  }
});

salesItemController.get("/", async (request, response, next) => {
  // From Express.js 5.x onwards, the try-catch block and calling next(error) is not needed
  try {
    const salesItemsQuery = await transformAndValidate(SalesItemsQuery, {
      ...request.query,
      page: Number(request.query.page),
    });

    const outputSalesItems = await salesItemService.getSalesItems(
      salesItemsQuery.nameContains,
      salesItemsQuery.page,
      salesItemsQuery.sortBy,
    );

    response.send(outputSalesItems);
  } catch (error) {
    next(error);
  }
});

salesItemController.get("/:id", async (request, response, next) => {
  // From Express.js 5.x onwards, the try-catch block and calling next(error) is not needed
  try {
    const outputSalesItems = await salesItemService.getSalesItem(
      request.params.id,
    );
    response.send(outputSalesItems);
  } catch (error) {
    next(error);
  }
});

salesItemController.put("/:id", async (request, response, next) => {
  // From Express.js 5.x onwards, the try-catch block and calling next(error) is not needed
  try {
    const inputSalesItem = await transformAndValidate(
      InputSalesItem,
      request.body as object,
    );

    await salesItemService.updateSalesItem(request.params.id, inputSalesItem);
    response.sendStatus(HttpStatus.NO_CONTENT);
  } catch (error) {
    next(error);
  }
});

salesItemController.delete(
  "/:id",
  auditLoggingMiddleware,
  /* rbacMiddlewareFactory.createRbacMiddleware(["admin")] */
  async (request, response, next) => {
    // From Express.js 5.x onwards, the try-catch block and calling next(error) is not needed
    try {
      await salesItemService.deleteSalesItem(request.params.id);
      response.sendStatus(HttpStatus.NO_CONTENT);
    } catch (error) {
      next(error);
    }
  },
);

export default salesItemController;
