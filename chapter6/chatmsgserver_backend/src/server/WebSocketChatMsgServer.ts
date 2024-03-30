import WebSocket, { WebSocketServer } from 'ws';
import phoneNbrToConnMap from '../connection/phoneNbrToConnMap';
import { ChatMessage } from '../service/ChatMessage';
import WebSocketConnection from '../connection/WebSocketConnection';
import PhoneNbrToInstanceUuidCache from '../cache/PhoneNbrToInstanceUuidCache';
import RedisPhoneNbrToInstanceUuidCache from '../cache/RedisPhoneNbrToInstanceUuidCache';
import redisClient from '../cache/redisClient';

export default class WebSocketChatMsgServer {
  private readonly webSocketServer: WebSocketServer;

  private readonly cache: PhoneNbrToInstanceUuidCache =
    new RedisPhoneNbrToInstanceUuidCache(redisClient);

  private readonly wsToPhoneNbrMap = new Map<WebSocket, string>();

  constructor(private readonly instanceUuid: string) {
    this.webSocketServer = new WebSocketServer({ port: 8080 });

    this.webSocketServer.on('connection', (webSocket: WebSocket) => {
      webSocket.on('message', async (chatMessageJson) => {
        let chatMessage: ChatMessage;

        try {
          // Validate chat message JSON ...
          chatMessage = JSON.parse(chatMessageJson.toString());
        } catch {
          // Handle error
          return;
        }

        const senderPhoneNumber = chatMessage.senderPhoneNbr;
        const webSocketConnection = new WebSocketConnection(webSocket);
        phoneNbrToConnMap.set(senderPhoneNumber, webSocketConnection);
        this.wsToPhoneNbrMap.set(webSocket, senderPhoneNumber);

        try {
          this.cache.tryStore(senderPhoneNumber, this.instanceUuid);
        } catch (error) {
          // Handle error
        }

        this.chatMsgService.send(chatMessage);
      });

      webSocket.on('error', () => {
        // Handle error ...
      });

      webSocket.on('close', () => {
        this.close(webSocket);
      });
    });
  }

  closeServer() {
    this.webSocketServer.close();
    this.webSocketServer.clients.forEach((client) => client.close());
  }

  async close(webSocket: WebSocket) {
    const phoneNumber = this.wsToPhoneNbrMap.get(webSocket);

    if (phoneNumber) {
      phoneNbrToConnMap.delete(phoneNumber);

      try {
        await this.cache.tryRemove(phoneNumber);
      } catch (error) {
        // Handle error
      }
    }

    this.wsToPhoneNbrMap.delete(webSocket);
  }
}
