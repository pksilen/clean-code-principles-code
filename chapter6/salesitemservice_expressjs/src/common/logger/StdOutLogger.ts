import { Logger, LogLevel, logLevelToSeverity } from "./Logger";

class StdOutLogger implements Logger {
  private readonly loggingLevelSeverity: number =
    logLevelToSeverity[process.env.LOG_LEVEL || "WARN"] ?? 4;

  log(logLevel: LogLevel, message: string): void {
    const logLevelSeverity = logLevelToSeverity[logLevel];

    if (logLevelSeverity >= this.loggingLevelSeverity) {
      console.log(`${logLevel}: ${message}`);
    }
  }
}

const logger = new StdOutLogger();
export default logger;
