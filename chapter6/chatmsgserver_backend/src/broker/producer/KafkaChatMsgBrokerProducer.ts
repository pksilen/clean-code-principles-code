import AbstractChatMsgBrokerProducer from './AbstractChatMsgBrokerProducer';
import { Kafka, Producer } from 'kafkajs';

export default class KafkaChatMsgBrokerProducer extends AbstractChatMsgBrokerProducer {
  private readonly kafkaProducer: Producer;

  constructor(kafkaClient: Kafka) {
    super();
    this.kafkaProducer = kafkaClient.producer();
  }

  async tryProduce(chatMessage: ChatMessage, topic: string): Promise<void> {
    try {
      await this.kafkaProducer.connect();

      await this.kafkaProducer.send({
        topic,
        messages: [{ value: JSON.stringify(chatMessage) }],
      });
    } catch {
      // Handle error
    }
  }

  async close(): Promise<void> {
    try {
      await this.kafkaProducer.disconnect();
    } catch {
      // Handle error
    }
  }
}
