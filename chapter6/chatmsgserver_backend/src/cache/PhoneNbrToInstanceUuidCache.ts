export default interface PhoneNbrToInstanceUuidCache {
  retrieveInstanceUuid(phoneNumber: string | undefined): Promise<string | null>;
  tryStore(phoneNumber: string, instanceUuid: string): Promise<void>;
  tryRemove(phoneNumber: string): Promise<void>;
}
