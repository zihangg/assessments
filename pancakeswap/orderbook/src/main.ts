import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { MicroserviceOptions, Transport } from '@nestjs/microservices';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  app.connectMicroservice<MicroserviceOptions>({
    transport: Transport.KAFKA,
    options: {
      client: {
        clientId: 'orderbook',
        brokers: ['kafka:9092'],
      },
      consumer: {
        groupId: 'orderbook-consumer',
      },
    },
  });

  await app.listen(3001);

  try {
    await app.startAllMicroservices();
  } catch (err) {
    return console.log(err);
  }
}
bootstrap();
