export default interface ChatMsgBrokerAdminClient {
  tryCreateTopic(name: string): Promise<void>;
}
