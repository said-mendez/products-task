package com.skytouch.management.service;


import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.management.service.implementation.ProductServiceRabbitMQImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ProductServiceRabbitMQTest {
    @Autowired
    ProductServiceRabbitMQImpl productServiceRabbitMQ;

    @Test
    public void addProduct() {
        // Given:
        String randomName = UUID.randomUUID().toString();
        Product product = new Product();
        product.setName(randomName);
        product.setDescription("Description " + randomName);
        product.setPrice(new BigDecimal("45.67"));

        // When:
        ResponseStatus responseStatus = productServiceRabbitMQ.addProduct(product);

        // Then:
        assertThat(responseStatus.getSuccess()).isTrue();
        assertThat(responseStatus.getMessage()).isEqualTo("Product " + product.getName() + " was created.");
    }

    @Test
    public void addProductWithoutRequiredAttributes() {
        // Given:
        String randomName = UUID.randomUUID().toString();
        Product product = new Product();
        product.setDescription("Description " + randomName);
        product.setPrice(new BigDecimal("45.67"));

        // When:
        ResponseStatus responseStatus = productServiceRabbitMQ.addProduct(product);

        // Then:
        assertThat(responseStatus.getSuccess()).isFalse();
        assertThat(responseStatus.getMessage()).isEqualTo("Something went wrong while trying to add a product: ");
        assertThat(responseStatus.getException()).isEqualTo("Product is null!");
    }

    @Test
    public void addingDuplicateProduct() {
        // Given:
        String randomName = UUID.randomUUID().toString();
        Product product = new Product();
        product.setName(randomName);
        product.setDescription("Description " + randomName);
        product.setPrice(new BigDecimal("45.67"));
        productServiceRabbitMQ.addProduct(product);

        Product product2 = new Product();
        product2.setName(randomName);
        product2.setDescription("Description " + randomName);
        product2.setPrice(new BigDecimal("45.67"));

        // When:
        ResponseStatus responseStatus = productServiceRabbitMQ.addProduct(product2);

        // Then:
        assertThat(responseStatus.getSuccess()).isFalse();
        assertThat(responseStatus.getMessage()).isEqualTo("Something went wrong while trying to add a product: ");
        assertThat(responseStatus.getException()).contains("Duplicate");
    }

    @Test
    public void listProducts() throws Exception {
        // Given:
        // When:
        ListProductsRequestResponse listProductsRequestResponse = productServiceRabbitMQ.listProducts();


        // Then:
        assertThat(listProductsRequestResponse.getResponseStatus().get("status").getSuccess()).isTrue();
        assertThat(listProductsRequestResponse.getResponseStatus().get("status").getMessage()).isEqualTo("Listing products");
    }
}
