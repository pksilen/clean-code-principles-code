import { AuditLogger } from "./AuditLogger";

export default class RemoteAuditLogger implements AuditLogger {
  log(message: string): void {
    // In real-life, implement sending the audit log message to a remote
    // audit logging system via a remote API call.
    console.log(message);
  }
}
