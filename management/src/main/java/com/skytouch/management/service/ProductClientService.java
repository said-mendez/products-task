package com.skytouch.management.service;

import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.skytouch.commonlibrary.config.RabbitMQConfig.EXCHANGE;
import static com.skytouch.commonlibrary.config.RabbitMQConfig.ADD_PRODUCTS_QUEUE;
import static com.skytouch.commonlibrary.config.RabbitMQConfig.ADD_PRODUCTS_KEY;

@Service
public class ProductClientService {
    private RabbitTemplate rabbitTemplate;

    public ProductClientService(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ResponseStatus addProduct(Product product) {
        ResponseStatus responseStatus = (ResponseStatus) rabbitTemplate.convertSendAndReceive(EXCHANGE, ADD_PRODUCTS_KEY, product);
        return responseStatus;
    }
}
