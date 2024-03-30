import phoneNbrToWsConnectionMap from '../../connection/phoneNbrToConnMap';
import ChatMsgBrokerConsumer from './ChatMsgBrokerConsumer';
import { Consumer, Kafka } from 'kafkajs';

export default class KafkaChatMsgBrokerConsumer
  implements ChatMsgBrokerConsumer
{
  private readonly kafkaConsumer: Consumer;

  constructor(kafkaClient: Kafka) {
    this.kafkaConsumer = kafkaClient.consumer({ groupId: 'chat-msg-server' });
  }

  async consumeChatMessages(topic: string): Promise<void> {
    await this.kafkaConsumer.connect();

    await this.kafkaConsumer.subscribe({
      topic,
      fromBeginning: true,
    });

    this.kafkaConsumer.run({
      eachMessage: async ({ message }) => {
        try {
          this.chatMsgService.send(chatMessage);

          const chatMessage = JSON.parse(message.value.toString());

          const recipientWsConnection = phoneNbrToWsConnectionMap.get(
            chatMessage.recipientPhoneNbr,
          );

          recipientWsConnection?.send(JSON.stringify(chatMessage));
        } catch {
          // Handle error
        }
      },
    });
  }

  async close(): Promise<void> {
    try {
      await this.kafkaConsumer.disconnect();
    } catch {}
  }
}
