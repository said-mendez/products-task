package com.skytouch.microservice.mapper;

import com.skytouch.commonlibrary.model.Product;
import com.skytouch.microservice.model.ProductDB;
import com.skytouch.microservice.service.mapper.ProductDBMapper;
import com.skytouch.microservice.service.mapper.ProductMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductMappersTest {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductDBMapper productDBMapper;

    @Test
    public void tryingToMapNullProductDBThrowsNullPointerException () {
        // Given:
        ProductDB productDB = new ProductDB();

        // When:
        // Then:
        assertThatThrownBy(() -> productMapper.apply(productDB))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Product is null!");
    }

    @Test
    public void tryingToMapNullProductThrowsNullPointerException () {
        // Given:
        Product product = new Product();

        // When:
        // Then:
        assertThatThrownBy(() -> productDBMapper.apply(product))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Product is null!");
    }
}
