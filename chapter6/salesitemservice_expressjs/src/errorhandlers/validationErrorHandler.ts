import { ValidationError } from "class-validator";
import HttpStatus from "http-status-codes";
import { createErrorResponse } from "../common/utils/utils";

export default function validationErrorHandler(error, request, response, next) {
  if (Array.isArray(error) && error?.[0] instanceof ValidationError) {
    response
      .status(HttpStatus.BAD_REQUEST)
      .send(
        createErrorResponse(
          error[0] as any,
          400,
          "RequestValidationError",
          request,
        ),
      );
  } else {
    next(error);
  }
}
