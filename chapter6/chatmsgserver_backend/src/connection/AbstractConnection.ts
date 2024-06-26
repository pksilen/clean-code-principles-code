import Connection from './Connection';
import ChatMsgServerError from '../errors/ChatMsgServerError';

export default abstract class AbstractConnection implements Connection {
  static Error = class extends ChatMsgServerError {};

  abstract send(message: string): void;
}
