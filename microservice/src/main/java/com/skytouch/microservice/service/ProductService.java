package com.skytouch.microservice.service;

import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.commonlibrary.model.ListProductsRequestResponse;

public interface ProductService {
    ListProductsRequestResponse fetchAllProducts();
    void addProduct(Product product);
}

