import AbstractChatMsgBrokerAdminClient from './AbstractChatMsgBrokerAdminClient';
import { Admin, Kafka } from 'kafkajs';

export default class KafkaChatMsgBrokerAdminClient extends AbstractChatMsgBrokerAdminClient {
  private readonly kafkaAdminClient: Admin;

  constructor(kafkaClient: Kafka) {
    super();
    this.kafkaAdminClient = kafkaClient.admin();
  }

  async tryCreateTopic(name: string): Promise<void> {
    try {
      await this.kafkaAdminClient.connect();

      await this.kafkaAdminClient.createTopics({
        topics: [{ topic: name }],
      });

      await this.kafkaAdminClient.disconnect();
    } catch {
      throw new KafkaChatMsgBrokerAdminClient.CreateTopicError();
    }
  }
}
