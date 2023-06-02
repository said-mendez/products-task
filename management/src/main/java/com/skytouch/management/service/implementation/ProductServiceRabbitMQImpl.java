package com.skytouch.management.service;

import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.skytouch.commonlibrary.config.RabbitMQConfig.*;

@Service
public class ProductServiceRabbitMQImpl implements ProductServiceRabbitMQ {
    private final RabbitTemplate rabbitTemplate;

    public ProductServiceRabbitMQImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public ResponseStatus addProduct(Product product) throws RuntimeException {
        ResponseStatus responseStatus = (ResponseStatus) rabbitTemplate.convertSendAndReceive(EXCHANGE, ADD_PRODUCTS_KEY, product);
        if (responseStatus == null)
            throw new RuntimeException("Microservice is not responding!");
        return responseStatus;
    }

    @Override
    public ListProductsRequestResponse listProducts() throws RuntimeException, Exception {
        ListProductsRequestResponse listProductsRequestResponse = (ListProductsRequestResponse) rabbitTemplate.convertSendAndReceive(EXCHANGE, LIST_PRODUCTS_KEY, "");
        if (listProductsRequestResponse == null)
            throw new RuntimeException("Microservice is not responding!");
        return listProductsRequestResponse;
    }
}
