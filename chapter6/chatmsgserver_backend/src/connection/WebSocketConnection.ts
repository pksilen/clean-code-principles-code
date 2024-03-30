import AbstractConnection from './AbstractConnection';
import WebSocket from 'ws';

export default class WebSocketConnection extends AbstractConnection {
  constructor(private readonly webSocket: WebSocket) {
    super();
  }

  send(message: string): void {
    this.webSocket.send(message);
  }
}
