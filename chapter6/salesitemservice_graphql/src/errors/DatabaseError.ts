import SalesItemServiceError from './SalesItemServiceError';

export default class DatabaseFoundError extends SalesItemServiceError {
  constructor(error: Error) {
    super(
      500,
      'Internal Server Error',
      'Database error',
      'DatabaseError',
      error.message,
      error,
    );
  }
}
