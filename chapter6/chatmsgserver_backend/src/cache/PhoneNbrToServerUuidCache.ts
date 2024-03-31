export default interface PhoneNbrToServerUuidCache {
  retrieveServerUuid(phoneNumber: string | undefined): Promise<string | null>;
  tryStore(phoneNumber: string, serverUuid: string): Promise<void>;
  tryRemove(phoneNumber: string): Promise<void>;
}
