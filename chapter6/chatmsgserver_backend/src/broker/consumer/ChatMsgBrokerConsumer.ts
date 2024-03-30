export default interface ChatMsgBrokerConsumer {
  consumeChatMessages(topic: string): Promise<void>;
  close(): Promise<void>;
}
