export default interface Connection {
  trySend(message: string): Promise<void>;
}
