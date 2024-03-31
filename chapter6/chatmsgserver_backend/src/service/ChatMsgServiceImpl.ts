import { ChatMsgService } from './ChatMsgService';
import KafkaChatMsgBrokerProducer from '../broker/producer/KafkaChatMsgBrokerProducer';
import kafkaClient from '../broker/kafkaClient';
import { ChatMessage } from './ChatMessage';
import PhoneNbrToServerUuidCache from '../cache/PhoneNbrToServerUuidCache';
import RedisPhoneNbrToServerUuidCache from '../cache/RedisPhoneNbrToServerUuidCache';
import redisClient from '../cache/redisClient';
import phoneNbrToConnMap from '../connection/phoneNbrToConnMap';

export default class ChatMsgServiceImpl implements ChatMsgService {
  private readonly chatMsgBrokerProducer = new KafkaChatMsgBrokerProducer(
    kafkaClient,
  );

  private readonly cache: PhoneNbrToServerUuidCache =
    new RedisPhoneNbrToServerUuidCache(redisClient);

  constructor(private readonly serverUuid: string) {}

  async trySend(chatMessage: ChatMessage): Promise<void> {
    const recipientServerUuid = await this.cache.retrieveServerUuid(
      chatMessage.recipientPhoneNbr,
    );

    if (recipientServerUuid === this.serverUuid) {
      // Recipient has active connection on
      // the same server instance as sender
      const recipientConnection = phoneNbrToConnMap.get(
        chatMessage.recipientPhoneNbr,
      );

      recipientConnection?.send(JSON.stringify(chatMessage));
    } else if (recipientServerUuid) {
      // Recipient has active connection on different
      // server instance compared to sender
      const topic = recipientServerUuid;
      await this.chatMsgBrokerProducer.tryProduce(chatMessage, topic);
    }
  }
}
