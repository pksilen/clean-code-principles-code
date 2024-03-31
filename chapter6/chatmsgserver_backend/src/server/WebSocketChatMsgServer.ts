import WebSocket, { WebSocketServer } from 'ws';
import phoneNbrToConnMap from '../connection/phoneNbrToConnMap';
import { ChatMessage } from '../service/ChatMessage';
import WebSocketConnection from '../connection/WebSocketConnection';
import PhoneNbrToServerUuidCache from '../cache/PhoneNbrToServerUuidCache';
import RedisPhoneNbrToServerUuidCache from '../cache/RedisPhoneNbrToServerUuidCache';
import redisClient from '../cache/redisClient';
import { ChatMsgService } from '../service/ChatMsgService';

export default class WebSocketChatMsgServer {
  private readonly webSocketServer: WebSocketServer;

  private readonly cache: PhoneNbrToServerUuidCache =
    new RedisPhoneNbrToServerUuidCache(redisClient);

  private readonly wsToPhoneNbrMap = new Map<WebSocket, string>();

  constructor(
    private readonly serverUuid: string,
    private readonly chatMsgService: ChatMsgService,
  ) {
    this.webSocketServer = new WebSocketServer({ port: 8000 });

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

        phoneNbrToConnMap.set(
          chatMessage.senderPhoneNbr,
          new WebSocketConnection(webSocket),
        );

        this.wsToPhoneNbrMap.set(webSocket, chatMessage.senderPhoneNbr);

        try {
          await this.cache.tryStore(
            chatMessage.senderPhoneNbr,
            this.serverUuid,
          );
        } catch (error) {
          // Handle error
        }

        if (chatMessage.message) {
          await this.chatMsgService.trySend(chatMessage);
        }
      });

      webSocket.on('error', () => {
        // Handle error ...
      });

      webSocket.on('close', async () => {
        await this.close(webSocket);
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
