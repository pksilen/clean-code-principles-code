import ChatMsgBrokerProducer from './ChatMsgBrokerProducer';
import ChatMsgServerError from '../../errors/ChatMsgServerError';
import { ChatMessage } from '../../service/ChatMessage';

export default abstract class AbstractChatMsgBrokerProducer
  implements ChatMsgBrokerProducer
{
  static Error = class extends ChatMsgServerError {};

  abstract tryProduce(chatMessage: ChatMessage, topic: string): Promise<void>;
  abstract tryClose(): Promise<void>;
}
