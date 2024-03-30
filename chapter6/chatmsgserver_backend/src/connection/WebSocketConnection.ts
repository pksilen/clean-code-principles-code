import AbstractConnection from './AbstractConnection';

export default class WebSocketConnection extends AbstractConnection {
  constructor(private readonly webSocket: WebSocket) {
    super();
  }

  trySend(message: string): Promise<void> {
    this.webSocket.send(message);
  }
}
