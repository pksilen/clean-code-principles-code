import { NestFactory } from '@nestjs/core';
import { AppModule } from './AppModule';
import { ValidationPipe } from '@nestjs/common';
import SalesItemServiceErrorFilter from './errorfilters/SalesItemServiceErrorFilter';
import { ValidationErrorFilter } from './errorfilters/ValidationErrorFilter';
import { ErrorFilter } from './errorfilters/ErrorFilter';
import { WsAdapter } from '@nestjs/platform-ws';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  app.useWebSocketAdapter(new WsAdapter(app));

  app.useGlobalPipes(
    new ValidationPipe({
      transform: true,
    }),
  );

  // Highest priority global filter is the last one
  app.useGlobalFilters(
    new ErrorFilter(),
    new SalesItemServiceErrorFilter(),
    new ValidationErrorFilter(),
  );

  await app.listen(8000);
}

bootstrap();
