package com.skytouch.microservice.service;

import com.skytouch.microservice.model.AddProductsRequestResponse;
import com.skytouch.microservice.model.ListProductsRequestResponse;
import com.skytouch.microservice.model.ProductDB;

import java.util.List;

public interface ProductService {
    ListProductsRequestResponse fetchAllProducts();
    AddProductsRequestResponse addProduct(ProductDB productDB);
}

