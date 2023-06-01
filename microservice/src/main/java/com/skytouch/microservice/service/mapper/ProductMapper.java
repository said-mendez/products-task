package com.skytouch.microservice.service.mapper;

import com.skytouch.commonlibrary.model.Product;
import com.skytouch.microservice.model.ProductDB;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductMapper implements Function<ProductDB, Product> {
    @Override
    public Product apply(ProductDB productDB) throws NullPointerException {
        if (productDB.getName() == null || productDB.getPrice() == null)
            throw  new NullPointerException("Required attributes are missing or product is null!");
        Product product = new Product();
        product.setId(productDB.getId());
        product.setName(productDB.getName());
        product.setDescription(productDB.getDescription());
        product.setPrice(productDB.getPrice());
        product.setCreatedAt(productDB.getCreatedAt());

        return product;
    }
}
