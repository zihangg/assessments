import { Module } from '@nestjs/common';
import { GatewayController } from './gateway.controller';
import { ClientsModule, Transport } from '@nestjs/microservices';

@Module({
  imports: [
    ClientsModule.register([
      {
        name: 'KAFKA_SERVICE',
        transport: Transport.KAFKA,
        options: {
          client: {
            clientId: 'gateway',
            brokers: ['kafka:9092'],
          },
          consumer: {
            groupId: 'gateway-consumer'
          },
          producerOnlyMode: true,
        }
      },
    ]),
  ],
  controllers: [GatewayController],
  
})
export class GatewayModule {}
