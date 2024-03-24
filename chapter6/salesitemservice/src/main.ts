import { NestFactory } from '@nestjs/core';
import { AppModule } from './AppModule';
import { ValidationPipe } from '@nestjs/common';
import SalesItemServiceErrorFilter from './errorfilters/SalesItemServiceErrorFilter';
import { ValidationErrorFilter } from './errorfilters/ValidationErrorFilter';
import { ErrorFilter } from './errorfilters/ErrorFilter';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  app.useGlobalPipes(new ValidationPipe());

  // Highest priority global filter is the last one
  app.useGlobalFilters(
    new ErrorFilter(),
    new SalesItemServiceErrorFilter(),
    new ValidationErrorFilter(),
  );

  await app.listen(8000);
}

bootstrap();
