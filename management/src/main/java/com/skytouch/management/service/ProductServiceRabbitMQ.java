package com.skytouch.management.service;

import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.commonlibrary.model.Product;
import com.skytouch.commonlibrary.model.ResponseStatus;

public interface ProductServiceRabbitMQ {
    ResponseStatus addProduct(Product product);
    ListProductsRequestResponse listProducts();
}
