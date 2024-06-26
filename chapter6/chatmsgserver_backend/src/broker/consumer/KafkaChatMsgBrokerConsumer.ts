import ChatMsgBrokerConsumer from './ChatMsgBrokerConsumer';
import { Consumer, Kafka } from 'kafkajs';
import { ChatMsgService } from '../../service/ChatMsgService';

export default class KafkaChatMsgBrokerConsumer
  implements ChatMsgBrokerConsumer
{
  private readonly kafkaConsumer: Consumer;

  constructor(
    kafkaClient: Kafka,
    private readonly chatMsgService: ChatMsgService,
  ) {
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
          if (message.value) {
            const chatMessage = JSON.parse(message.value.toString());
            this.chatMsgService.trySend(chatMessage);
          }
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
