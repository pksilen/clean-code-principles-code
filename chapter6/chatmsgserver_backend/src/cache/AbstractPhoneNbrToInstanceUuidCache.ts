import PhoneNbrToInstanceUuidCache from './PhoneNbrToInstanceUuidCache';
import ChatMsgServerError from '../errors/ChatMsgServerError';

export default abstract class AbstractPhoneNbrToInstanceUuidCache
  implements PhoneNbrToInstanceUuidCache
{
  static Error = class extends ChatMsgServerError {};

  abstract retrieveInstanceUuid(
    phoneNumber: string | undefined,
  ): Promise<string | null>;

  abstract tryStore(phoneNumber: string, instanceUuid: string): Promise<void>;
  abstract tryRemove(phoneNumber: string): Promise<void>;
}
