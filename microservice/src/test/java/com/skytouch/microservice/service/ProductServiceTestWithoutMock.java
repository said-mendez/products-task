package com.skytouch.microservice.service;

import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.microservice.model.ProductDB;
import com.skytouch.microservice.repository.ProductDao;
import com.skytouch.microservice.service.mapper.ProductMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static com.skytouch.commonlibrary.config.RabbitMQConfig.*;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class ProductServiceTestWithoutMock {
    @Autowired
    ProductDao productDao;
    @Autowired
    ProductService productService;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    @Transactional
    public void canListProducts() {
        // Given:
        List<Product> expectedProductsList = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            String randomName = UUID.randomUUID().toString();
            ProductDB productDB = new ProductDB();
            productDB.setName("Product " + randomName);
            productDB.setDescription("Product " + i + " Description");
            BigDecimal price = new BigDecimal("123.123123123");
            price = price.setScale(2, RoundingMode.HALF_EVEN);
            productDB.setPrice(price);
            expectedProductsList.add(productMapper.apply(productDB));
            productDao.addProduct(productDB.getName(), productDB.getDescription(), productDB.getPrice());
        }

        // When:
        ListProductsRequestResponse response = productService.fetchAllProducts();

        // Then:
        assertThat(response.getData().size()).isGreaterThanOrEqualTo(expectedProductsList.size());
    }

    @Test
    public void addProductUsingQueue() {
        // Given:
        String randomName = UUID.randomUUID().toString();
        Product product = new Product();
        product.setName(randomName);
        product.setDescription("New Product Description");
        BigDecimal price = new BigDecimal("123.123123123");
        price = price.setScale(2, RoundingMode.HALF_EVEN);
        product.setPrice(price);

        // When:
        ResponseStatus response = (ResponseStatus) rabbitTemplate.convertSendAndReceive(EXCHANGE, ADD_PRODUCTS_KEY, product);

        // Then:
        assertThat(response.getSuccess()).isTrue();
        assertThat(response.getMessage()).isEqualTo("Product " + product.getName() + " was created.");
        assertThat(response.getException()).isNull();
    }

    @Test
    @Transactional
    public void listProductsUsingQueue() {
        // Given:
        List<Product> expectedProductsList = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            String randomName = UUID.randomUUID().toString();
            ProductDB productDB = new ProductDB();
            productDB.setName("Product " + randomName);
            productDB.setDescription("Product " + i + " Description");
            BigDecimal price = new BigDecimal("123.123123123");
            price = price.setScale(2, RoundingMode.HALF_EVEN);
            productDB.setPrice(price);
            expectedProductsList.add(productMapper.apply(productDB));
            productDao.addProduct(productDB.getName(), productDB.getDescription(), productDB.getPrice());
        }

        // When:
        ListProductsRequestResponse listProductsRequestResponse = (ListProductsRequestResponse) rabbitTemplate.convertSendAndReceive(EXCHANGE, LIST_PRODUCTS_KEY, "");

        // Then:
        assertThat(listProductsRequestResponse.getData().size()).isGreaterThanOrEqualTo(expectedProductsList.size());
        assertThat(listProductsRequestResponse.getResponseStatus().get("status").getSuccess()).isTrue();
        assertThat(listProductsRequestResponse.getResponseStatus().get("status").getMessage()).isEqualTo("Listing products");
        assertThat(listProductsRequestResponse.getResponseStatus().get("status").getException()).isNull();
    }
}
