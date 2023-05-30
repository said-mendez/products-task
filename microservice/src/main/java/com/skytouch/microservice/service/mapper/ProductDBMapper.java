package com.skytouch.microservice.service.mapper;

import com.skytouch.commonlibrary.model.Product;
import com.skytouch.microservice.model.ProductDB;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDBMapper implements Function<Product, ProductDB> {
    @Override
    public ProductDB apply(Product product) throws NullPointerException {
        if (product == null)
            throw  new NullPointerException("Product is null!");
        ProductDB productDB = new ProductDB();
        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setPrice(product.getPrice());

        return productDB;
    }
}
