import AbstractPhoneNbrToServerUuidCache from './AbstractPhoneNbrToServerUuidCache';
import Redis from 'ioredis';

export default class RedisPhoneNbrToServerUuidCache extends AbstractPhoneNbrToServerUuidCache {
  constructor(private readonly redisClient: Redis) {
    super();
  }

  async retrieveServerUuid(
    phoneNumber: string | undefined,
  ): Promise<string | null> {
    let serverUuid: string | null = null;

    if (phoneNumber) {
      try {
        serverUuid = await this.redisClient.hget(
          'phoneNbrToServerUuidMap',
          phoneNumber,
        );
      } catch {
        // Handle error
      }
    }

    return serverUuid;
  }

  async tryStore(phoneNumber: string, serverUuid: string): Promise<void> {
    try {
      await this.redisClient.hset(
        'phoneNbrToServerUuidMap',
        phoneNumber,
        serverUuid,
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
