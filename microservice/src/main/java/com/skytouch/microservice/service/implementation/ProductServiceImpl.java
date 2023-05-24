package com.skytouch.microservice.service.implementation;

import com.skytouch.microservice.model.ProductDB;
import com.skytouch.microservice.repository.ProductDao;
import com.skytouch.microservice.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    @Override
    public List<ProductDB> fetchAllProducts() {
        List<ProductDB> products = productDao.findAllProducts();

        return products;
    }

    @Transactional
    @Override
    public ProductDB addProduct(ProductDB productDB) {
        ProductDB createdProductDB = productDao.addProduct(
                productDB.getName(),
                productDB.getDescription(),
                productDB.getPrice());

        return productDB;
    }
}
