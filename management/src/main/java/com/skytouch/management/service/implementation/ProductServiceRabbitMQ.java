package com.skytouch.management.service.implementation;

import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.management.exception.MicroserviceException;
import com.skytouch.management.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.skytouch.commonlibrary.config.RabbitMQConfig.*;

@Service
@Slf4j
public class ProductServiceRabbitMQ implements ProductService {
    private final RabbitTemplate rabbitTemplate;

    public ProductServiceRabbitMQ(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void addProduct(Product product) {
        rabbitTemplate.convertAndSend(EXCHANGE, ADD_PRODUCTS_KEY, product);
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
