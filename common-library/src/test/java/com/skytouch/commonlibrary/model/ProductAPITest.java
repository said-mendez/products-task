package com.skytouch.commonlibrary.model;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ProductAPITest {
    @Test
    public void createProductAndValidateAttributes() {
        // Given:
        String name = "Product 1";
        String description = "Product 1 Description";
        Boolean isDeleted = false;
        BigDecimal price = new BigDecimal("1234.55");
        Date createdAt = new Date();

        // When:
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(name);
        product.setDescription(description);
        product.setIsDeleted(isDeleted);
        product.setPrice(price);
        product.setCreatedAt(createdAt);
        product.setUpdatedAt(createdAt);

        // Then:
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getDescription()).isEqualTo(description);
        assertThat(product.getIsDeleted()).isEqualTo(isDeleted);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getCreatedAt()).isEqualTo(createdAt);
        assertThat(product.getUpdatedAt()).isEqualTo(createdAt);
    }
}
