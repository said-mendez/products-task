package com.skytouch.microservice.service;

import com.skytouch.microservice.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductDao productDao;

    @Autowired
    private ProductService productService;
}
