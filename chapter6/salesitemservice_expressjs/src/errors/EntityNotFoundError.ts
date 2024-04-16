import SalesItemServiceError from './SalesItemServiceError';

export default class EntityNotFoundError extends SalesItemServiceError {
  constructor(entityName: string, entityId: string) {
    super(
      404,
      'Not Found',
      `${entityName} with id ${entityId} not found`,
      'EntityNotFound',
    );
  }
}
