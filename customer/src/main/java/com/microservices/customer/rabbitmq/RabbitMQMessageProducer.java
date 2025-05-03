package com.microservices.customer.rabbitmq;

import com.microservices.customer.dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQMessageProducer {

    RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(NotificationRequest payload, String exchange, String routingKey) {
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
    }
}
