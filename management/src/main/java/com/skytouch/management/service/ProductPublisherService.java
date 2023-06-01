package com.skytouch.management.service;

import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.skytouch.commonlibrary.config.RabbitMQConfig.*;

@Service
public class ProductPublisherService {
    private final RabbitTemplate rabbitTemplate;

    public ProductPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ResponseStatus addProduct(Product product) throws RuntimeException {
        ResponseStatus responseStatus = (ResponseStatus) rabbitTemplate.convertSendAndReceive(EXCHANGE, ADD_PRODUCTS_KEY, product);
        if (responseStatus == null)
            throw new RuntimeException("Microservice is not responding!");
        return responseStatus;
    }

    public ListProductsRequestResponse listProducts() throws RuntimeException, Exception {
        ListProductsRequestResponse listProductsRequestResponse = (ListProductsRequestResponse) rabbitTemplate.convertSendAndReceive(EXCHANGE, LIST_PRODUCTS_KEY, "");
        if (listProductsRequestResponse == null)
            throw new RuntimeException("Microservice is not responding!");
        return listProductsRequestResponse;
    }
}
