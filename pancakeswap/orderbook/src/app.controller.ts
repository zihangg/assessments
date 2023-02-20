import { Controller, Inject } from '@nestjs/common';
import { AppService } from './app.service';
import {
  ClientKafka,
  EventPattern,
} from '@nestjs/microservices';

@Controller()
export class AppController {
  constructor(@Inject('KAFKA_SERVICE') private readonly kafka: ClientKafka) {}

  @EventPattern('order_created')
  async handleOrderCreation(data: Record<string, unknown>) {
    console.log(data);
  }
}
