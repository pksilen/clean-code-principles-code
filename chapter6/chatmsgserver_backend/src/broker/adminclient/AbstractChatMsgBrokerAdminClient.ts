import ChatMsgBrokerAdminClient from './ChatMsgBrokerAdminClient';
import ChatMsgServerError from '../../errors/ChatMsgServerError';

export default abstract class AbstractChatMsgBrokerAdminClient
  implements ChatMsgBrokerAdminClient
{
  static CreateTopicError = class extends ChatMsgServerError {};

  abstract tryCreateTopic(name: string): Promise<void>;
}
