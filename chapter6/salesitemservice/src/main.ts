import { NestFactory } from '@nestjs/core';
import { AppModule } from './AppModule';
import { ValidationPipe } from '@nestjs/common';
import SalesItemServiceErrorFilter from './errorfilters/SalesItemServiceErrorFilter';
import { ValidationErrorFilter } from './errorfilters/ValidationErrorFilter';
import { ErrorFilter } from './errorfilters/ErrorFilter';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  app.useGlobalPipes(new ValidationPipe());

  app.useGlobalFilters(
    new SalesItemServiceErrorFilter(),
    new ValidationErrorFilter(),
    new ErrorFilter(),
  );

  await app.listen(3000);
}

bootstrap();
