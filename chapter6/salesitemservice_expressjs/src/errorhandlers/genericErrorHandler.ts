import HttpStatus from "http-status-codes";
import { createErrorResponse } from "../common/utils/utils";

export default function genericErrorHandler(error, request, response, next) {
  response
    .status(HttpStatus.INTERNAL_SERVER_ERROR)
    .json(createErrorResponse(error, 500, "UnspecifiedInternalError", request));
}
