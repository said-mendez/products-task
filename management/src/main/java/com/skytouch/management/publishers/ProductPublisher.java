package com.skytouch.management.publishers;

import com.skytouch.commonlibrary.config.QueuesConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisher {
    private final RabbitTemplate rabbitTemplate;

    public ProductPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish() {
        rabbitTemplate.convertAndSend(QueuesConfig.EXCHANGE, QueuesConfig.LIST_PRODUCTS_KEY, "Hello List Products!");
    }

    public void publishAdd() {
        rabbitTemplate.convertAndSend(QueuesConfig.EXCHANGE, QueuesConfig.ADD_PRODUCTS_KEY, "Hello Add Products!");
    }
}
