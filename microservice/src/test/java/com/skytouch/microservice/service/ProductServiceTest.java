package com.skytouch.microservice.service;

import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.microservice.model.ProductDB;
import com.skytouch.microservice.repository.ProductDao;
import com.skytouch.microservice.service.mapper.ProductMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {
    @MockBean
    private ProductDao productDao;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @Test
    public void canAddAProduct() {
        // Given:
        String randoName = UUID.randomUUID().toString();
        ProductDB productDB = new ProductDB();
        productDB.setName(randoName);
        productDB.setDescription("New Product Description");
        BigDecimal price = new BigDecimal("123.123123123");
        price = price.setScale(2, RoundingMode.HALF_EVEN);
        productDB.setPrice(price);

        // When:
        when(productDao.addProduct(
                productDB.getName(),
                productDB.getDescription(),
                productDB.getPrice()))
                .thenReturn(productDB);

        ResponseStatus responseStatus = productService.addProduct(productMapper.apply(productDB));

        // Then:
        assertThat(responseStatus.getSuccess()).isEqualTo(true);
        assertThat(responseStatus.getMessage()).isEqualTo("Product " + randoName + " was created.");
    }

    @Test
    public void tryingToAddDuplicatedProduct() {
        // Given:
        ProductDB productDB = new ProductDB();
        productDB.setDescription("New Product Description");
        BigDecimal price = new BigDecimal("123.123123123");
        price = price.setScale(2, RoundingMode.HALF_EVEN);
        productDB.setPrice(price);

        // When:
        when(productDao.addProduct(
                productDB.getName(),
                productDB.getDescription(),
                productDB.getPrice()))
                .thenReturn(productDB);

        ResponseStatus responseStatus = productService.addProduct(productMapper.apply(productDB));

        // Then:
        System.out.println(responseStatus);
    }

    @Test
    public void tryingToAddNullProductReturnsError() {
        // Given:
        String ADD_PRODUCT_ERROR_MESSAGE = "Something went wrong while trying to add a product: ";
        Product product = new Product();

        // When:
        // Then:
        ResponseStatus responseStatus = productService.addProduct(product);
        assertThat(responseStatus.getSuccess()).isEqualTo(false);
        assertThat(responseStatus.getMessage()).isEqualTo(ADD_PRODUCT_ERROR_MESSAGE);
        assertThat(responseStatus.getException()).isEqualTo("Product is null!");
    }

}
