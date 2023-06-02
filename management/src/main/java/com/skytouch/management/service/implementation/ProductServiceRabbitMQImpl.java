package com.skytouch.management.service.implementation;

import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.management.exception.MicroserviceException;
import com.skytouch.management.service.ProductServiceRabbitMQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.skytouch.commonlibrary.config.RabbitMQConfig.*;

@Service
@Slf4j
public class ProductServiceRabbitMQImpl implements ProductServiceRabbitMQ {
    private final RabbitTemplate rabbitTemplate;

    public ProductServiceRabbitMQImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public ResponseStatus addProduct(Product product) {
        ResponseStatus responseStatus = (ResponseStatus) rabbitTemplate.convertSendAndReceive(EXCHANGE, ADD_PRODUCTS_KEY, product);
        if (responseStatus == null) {
            log.error("Error calling Microservice: ", "Microservice is not responding!");
            throw new MicroserviceException("Microservice is not responding!");
        }
        return responseStatus;
    }

    @Override
    public ListProductsRequestResponse listProducts() {
        ListProductsRequestResponse listProductsRequestResponse = (ListProductsRequestResponse) rabbitTemplate.convertSendAndReceive(EXCHANGE, LIST_PRODUCTS_KEY, "");
        if (listProductsRequestResponse == null) {
            log.error("Microservice did not provide a response on products retrieval");
            throw new MicroserviceException("Microservice is not responding!");
        }
        return listProductsRequestResponse;
    }
}
