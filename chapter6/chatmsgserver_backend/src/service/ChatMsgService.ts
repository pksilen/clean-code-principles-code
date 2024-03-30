import { ChatMessage } from './ChatMessage';

export interface ChatMsgService {
  trySend(chatMessage: ChatMessage): Promise<void>;
}
