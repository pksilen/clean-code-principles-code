import { WebSocketServer } from 'ws';
import kafkaClient from './kafkaClient.js';
import redisClient from './redisClient.js';

import phoneNbrToWsConnectionMap
  from './phoneNbrToWsConnectionMap.js';

import KafkaMessageBrokerProducer
  from './KafkaMessageBrokerProducer.js';


export default class WebSocketChatMsgServer {
  wsServer;
  messageBrokerProducer;
  wsConnectionToPhoneNbrMap = new Map();

  constructor(private readonly instanceUuid) {
    this.chatMsgBrokerProducer = new KafkaChatMsgBrokerProducer(kafkaClient);
    this.webSocketServer = new WebSocketServer({ port: 8080 });

    this.wsServer.on('connection', wsConnection => {
      phone_nbr_to_conn_map[phone_number] = connection
      self.__conn_to_phone_nbr_map[connection] = phone_number
      cache.tryStore(phoneNumber, self.__instance_uuid)

      wsConnection.on('message', async (chatMessageJson) => {
        try {
          const chatMessage = this.parse(chatMessageJson);
        } catch {
          // ...
        }

        // Validate chatMessage ...
        // Store chat message permanently using another API ...

        const recipientServerUuid =
          await this.getServerUuid(chatMessage.recipientPhoneNbr);

        this.send(chatMessage, recipientInstanceUuid);
      });

      wsConnection.on('close', () => {
        this.close(wsConnection);
      });
    });
  }

  closeServer() {
    this.wsServer.close();
    this.wsServer.clients.forEach(client => client.close());
    this.messageBrokerProducer.close();
  }

  send(chatMessage, serverUuid) {
    // Extract this method to: ChatMsgService.send(chatMessage)

    if (serverUuid === this.serverUuid) {
      // Recipient has active connection on
      // the same server instance as sender
      const recipientWsConnection =
        phoneNbrToWsConnectionMap
          .get(chatMessage.recipientPhoneNbr);

      recipientWsConnection?
    .send(JSON.stringify(chatMessage));

    } else if (serverUuid) {
      // Recipient has active connection on different
      // server instance compared to sender
      const serverTopic = serverUuid;

      this.messageBrokerProducer
        .produce(chatMessage, serverTopic);
    }
  }

  async close(wsConnection) {
    const phoneNbr =
      this.wsConnectionToPhoneNbrMap
        .get(wsConnection);

    phoneNbrToWsConnectionMap.delete(phoneNbr);
    this.wsConnectionToPhoneNbrMap.delete(wsConnection);
    cache.tryRemove()
  }
}
