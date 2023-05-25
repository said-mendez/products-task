package com.skytouch.microservice.service.implementation;

import com.skytouch.microservice.model.AddProductsRequestResponse;
import com.skytouch.microservice.model.ListProductsRequestResponse;
import com.skytouch.microservice.model.ProductDB;
import com.skytouch.microservice.model.ResponseStatus;
import com.skytouch.microservice.repository.ProductDao;
import com.skytouch.microservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public ListProductsRequestResponse fetchAllProducts() {
        ListProductsRequestResponse listProductsRequestResponse;
        ResponseStatus responseStatus;

        try {
            List<ProductDB> products = productDao.findAllProducts();

            responseStatus = new ResponseStatus(true, "Listing products.", null);
            listProductsRequestResponse = new ListProductsRequestResponse(products, responseStatus);

        } catch (Exception exception) {
            log.error("Something went wrong while trying to list products => ", exception);
            responseStatus = new ResponseStatus(false, "Something went wrong while trying to list products.", exception.getMessage());
            listProductsRequestResponse = new ListProductsRequestResponse(null, responseStatus);
        }

        return listProductsRequestResponse;
    }
    
    @Override
    public AddProductsRequestResponse addProduct(ProductDB productDB) {
        AddProductsRequestResponse addProductsRequestResponse;
        ResponseStatus responseStatus;
        try {
            ProductDB createdProductDB = productDao.addProduct(
                    productDB.getName(),
                    productDB.getDescription(),
                    productDB.getPrice());

            responseStatus = new ResponseStatus(true, "Product added.", null);
            addProductsRequestResponse = new AddProductsRequestResponse(createdProductDB, responseStatus);
        } catch (Exception exception) {
            log.error("Something went wrong while trying to add a product => ", exception);
            responseStatus = new ResponseStatus(false, "Something went wrong while trying to add a product.", exception.getMessage());
            addProductsRequestResponse = new AddProductsRequestResponse(null, responseStatus);
        }

        return addProductsRequestResponse;
    }
}
