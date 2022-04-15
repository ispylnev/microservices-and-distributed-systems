package com.amqp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public record RabbitMqMessageProducer (AmqpTemplate amqpTemplate) {
    public void publish(Object payload, String exchange, String routingKey) {
        log.info("try to publish the message to the {} using routingKey {}. Payload : {}",
                exchange, routingKey, payload);
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
        log.info("message has been published the message to the {} using routingKey {}. Payload : {}",
                exchange, routingKey, payload);
    }
}
