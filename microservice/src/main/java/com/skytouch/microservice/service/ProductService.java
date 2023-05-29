package com.skytouch.microservice.service;

import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.microservice.model.AddProductsRequestResponse;
import com.skytouch.microservice.model.ListProductsRequestResponse;
import com.skytouch.microservice.model.ProductDB;

public interface ProductService {
    ListProductsRequestResponse fetchAllProducts();
    ResponseStatus addProduct(Product product);
}

