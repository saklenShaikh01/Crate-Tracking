package com.example.inventory_service.messaging;


import com.example.inventory_service.Dto.CrateEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.example.inventory_service.config.RabbitMQConfig.*;

@Service
public class MessageProducer {

  private final RabbitTemplate rabbitTemplate;

  public MessageProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendEvent(CrateEvent event) {
    rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, event);
  }
}
