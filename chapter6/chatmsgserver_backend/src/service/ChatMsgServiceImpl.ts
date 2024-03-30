import { ChatMsgService } from './ChatMsgService';
import KafkaChatMsgBrokerProducer from '../broker/producer/KafkaChatMsgBrokerProducer';
import kafkaClient from '../broker/kafkaClient';
import { ChatMessage } from './ChatMessage';
import PhoneNbrToInstanceUuidCache from '../cache/PhoneNbrToInstanceUuidCache';
import RedisPhoneNbrToInstanceUuidCache from '../cache/RedisPhoneNbrToInstanceUuidCache';
import redisClient from '../cache/redisClient';
import phoneNbrToConnMap from '../connection/phoneNbrToConnMap';

export default class ChatMsgServiceImpl implements ChatMsgService {
  private readonly chatMsgBrokerProducer = new KafkaChatMsgBrokerProducer(
    kafkaClient,
  );

  private readonly cache: PhoneNbrToInstanceUuidCache =
    new RedisPhoneNbrToInstanceUuidCache(redisClient);

  constructor(private readonly instanceUuid: string) {}

  async trySend(chatMessage: ChatMessage): Promise<void> {
    const recipientInstanceUuid = await this.cache.retrieveInstanceUuid(
      chatMessage.recipientPhoneNbr,
    );

    if (recipientInstanceUuid === this.instanceUuid) {
      // Recipient has active connection on
      // the same server instance as sender
      const recipientConnection = phoneNbrToConnMap.get(
        chatMessage.recipientPhoneNbr,
      );

      recipientConnection?.send(JSON.stringify(chatMessage));
    } else if (recipientInstanceUuid) {
      // Recipient has active connection on different
      // server instance compared to sender
      const topic = recipientInstanceUuid;
      await this.chatMsgBrokerProducer.tryProduce(chatMessage, topic);
    }
  }
}
