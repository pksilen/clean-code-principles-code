import RemoteAuditLogger from "../common/logger/audit/RemoteAuditLogger";

const auditLogger = new RemoteAuditLogger();

export default function auditLoggingMiddleware(request, response, next) {
  auditLogger.log(
    `Endpoint ${request.method} ${request.url} accessed from ${request.ip}`,
  );

  next();
}
