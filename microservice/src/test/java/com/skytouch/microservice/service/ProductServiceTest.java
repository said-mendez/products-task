package com.skytouch.microservice.service;

import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
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
        String randomName = UUID.randomUUID().toString();
        ProductDB productDB = new ProductDB();
        productDB.setName(randomName);
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

        productService.addProduct(productMapper.apply(productDB));

        // Then:
        when(productDao.findByName(randomName)).thenReturn(productDB).equals(productDB);
    }

    @Test
    public void tryingToListProductsThrowsException() {
        // Given:
        when(productDao.findAllProducts()).thenThrow(new RuntimeException("Forced exception!"));

        // When:
        ListProductsRequestResponse listProductsRequestResponse = productService.fetchAllProducts();

        // Then:
        assertThat(listProductsRequestResponse.getResponseStatus().get("status").getSuccess()).isFalse();
        assertThat(listProductsRequestResponse.getResponseStatus().get("status").getMessage()).isEqualTo("Something went wrong while trying to list products: ");
        assertThat(listProductsRequestResponse.getResponseStatus().get("status").getException()).isEqualTo("Forced exception!");
    }
}
