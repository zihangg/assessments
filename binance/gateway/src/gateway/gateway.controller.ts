import { Body, Controller, Get, Inject, Post } from '@nestjs/common';
import { PostOrder } from 'src/dtos/PostOrder';
import { ClientKafka } from '@nestjs/microservices';

@Controller('gateway')
export class GatewayController {
constructor(@Inject('KAFKA_SERVICE') private readonly kafka: ClientKafka) {}

  @Post('postOrder')
  postOrder(@Body() body: PostOrder): any {
    const sampleOrder: PostOrder = { token: 'BNB', amount: 10, orderType: 1 };
    return this.kafka.emit('order_created', { order: sampleOrder });
  }
}
