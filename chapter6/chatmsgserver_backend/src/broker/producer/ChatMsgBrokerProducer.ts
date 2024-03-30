export default interface ChatMsgBrokerProducer {
  tryProduce(chatMessage: ChatMessage, topic: string): Promise<void>;
  tryClose(): Promise<void>;
}
