import SalesItemServiceError from "../errors/SalesItemServiceError";

export default function salesItemServiceErrorHandler(
  error,
  request,
  response,
  next,
) {
  if (error instanceof SalesItemServiceError) {
    response.status(error.statusCode).send(error.toResponse(request));
  } else {
    next(error);
  }
}
