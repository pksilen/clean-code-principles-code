import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import ApiErrorFilter from './ApiErrorFilter';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  app.useGlobalFilters(new ApiErrorFilter());
  await app.listen(8000);
}

bootstrap();
