package com.skytouch.management.service;


import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.management.exception.MicroserviceException;
import com.skytouch.management.service.implementation.ProductServiceRabbitMQ;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ProductServiceRabbitMQTest {
    @Autowired
    ProductServiceRabbitMQ productServiceRabbitMQ;

    @Test(expected = MicroserviceException.class)
    public void addingProductWithMicroserviceDownThrowsMicroserviceException() {
        // Given:
        String randomName = UUID.randomUUID().toString();
        Product product = new Product();
        product.setName(randomName);
        product.setDescription("Description " + randomName);
        product.setPrice(new BigDecimal("45.67"));

        // When:
        // Then:
        productServiceRabbitMQ.addProduct(product);

    }

    @Test(expected = MicroserviceException.class)
    public void listingProductsWithMicroserviceDownThrowsMicroserviceException() {
        // Given:
        // When:
        // Then:
        productServiceRabbitMQ.listProducts();

    }
}
