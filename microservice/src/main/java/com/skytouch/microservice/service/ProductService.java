package com.skytouch.microservice.service;

import com.skytouch.microservice.model.ProductDB;

import java.util.List;

public interface ProductService {
    List<ProductDB> fetchAllProducts();
    ProductDB addProduct(ProductDB productDB);
}

