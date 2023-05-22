package com.skytouch.management.publishers;

import com.skytouch.commonlibrary.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisher {
    private final RabbitTemplate rabbitTemplate;

    public ProductPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.LIST_PRODUCTS_KEY, "Hello List Products!");
    }

    public void publishAdd() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ADD_PRODUCTS_KEY, "Hello Add Products!");
    }
}
