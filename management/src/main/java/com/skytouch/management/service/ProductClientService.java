package com.skytouch.management.service;

import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.skytouch.commonlibrary.config.RabbitMQConfig.*;

@Service
public class ProductClientService {
    private final RabbitTemplate rabbitTemplate;

    public ProductClientService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ResponseStatus addProduct(Product product) {
        return (ResponseStatus) rabbitTemplate.convertSendAndReceive(EXCHANGE, ADD_PRODUCTS_KEY, product);
    }

    public ListProductsRequestResponse listProducts() {
        return (ListProductsRequestResponse) rabbitTemplate.convertSendAndReceive(EXCHANGE, LIST_PRODUCTS_KEY, "");
    }
}
