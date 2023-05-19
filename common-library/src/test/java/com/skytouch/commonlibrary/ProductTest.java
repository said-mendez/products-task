package com.skytouch.commonlibrary;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class ProductTest {
    @Test
    public void createProduct() {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName("Product 1");
        product.setDescription("Product 1 Description");
        product.setIsDeleted(false);
        product.setPrice(new BigDecimal(1234.45));
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
    }
}
