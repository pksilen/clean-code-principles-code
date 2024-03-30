import AbstractPhoneNbrToInstanceUuidCache from './AbstractPhoneNbrToInstanceUuidCache';
import Redis from 'ioredis';

export default class RedisPhoneNbrToInstanceUuidCache extends AbstractPhoneNbrToInstanceUuidCache {
  constructor(private readonly redisClient: Redis) {
    super();
  }

  async retrieveInstanceUuid(
    phoneNumber: string | undefined,
  ): Promise<string | null> {
    let instanceUuid: string | null = null;

    if (phoneNumber) {
      try {
        instanceUuid = await this.redisClient.hget(
          'phoneNbrToServerUuidMap',
          phoneNumber,
        );
      } catch {
        // Handle error
      }
    }

    return instanceUuid;
  }

  async tryStore(phoneNumber: string, instanceUuid: string): Promise<void> {
    try {
      await this.redisClient.hset(
        'phoneNbrToServerUuidMap',
        phoneNumber,
        instanceUuid,
      );
    } catch {
      // Handle error
    }
  }

  async tryRemove(phoneNumber: string): Promise<void> {
    try {
      await this.redisClient.hdel('phoneNbrToServerUuidMap', phoneNumber);
    } catch {
      // Handle error
    }
  }
}
