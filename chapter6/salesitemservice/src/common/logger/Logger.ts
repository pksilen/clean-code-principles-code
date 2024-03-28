export const logLevelToSeverity = {
  ERROR: 5,
  WARN: 4,
  INFO: 3,
  DEBUG: 2,
  TRACE: 1,
};

export type LogLevel = keyof typeof logLevelToSeverity;

export interface Logger {
  log(logLevel: LogLevel, message: string): void;
}
