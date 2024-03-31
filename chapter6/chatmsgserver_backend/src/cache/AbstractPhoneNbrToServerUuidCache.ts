import PhoneNbrToServerUuidCache from './PhoneNbrToServerUuidCache';
import ChatMsgServerError from '../errors/ChatMsgServerError';

export default abstract class AbstractPhoneNbrToServerUuidCache
  implements PhoneNbrToServerUuidCache
{
  static Error = class extends ChatMsgServerError {};

  abstract retrieveServerUuid(
    phoneNumber: string | undefined,
  ): Promise<string | null>;

  abstract tryStore(phoneNumber: string, instanceUuid: string): Promise<void>;
  abstract tryRemove(phoneNumber: string): Promise<void>;
}
