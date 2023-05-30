package com.skytouch.microservice.service.implementation;

import com.skytouch.commonlibrary.model.Product;
import com.skytouch.microservice.model.AddProductsRequestResponse;
import com.skytouch.commonlibrary.model.ListProductsRequestResponse;
import com.skytouch.microservice.model.ProductDB;
import com.skytouch.commonlibrary.model.ResponseStatus;
import com.skytouch.microservice.repository.ProductDao;
import com.skytouch.microservice.service.ProductService;
import com.skytouch.microservice.service.mapper.ProductDBMapper;
import com.skytouch.microservice.service.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.skytouch.commonlibrary.config.RabbitMQConfig.ADD_PRODUCTS_QUEUE;
import static com.skytouch.commonlibrary.config.RabbitMQConfig.LIST_PRODUCTS_QUEUE;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final ProductDBMapper productDBMapper;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductDao productDao, ProductDBMapper productDBMapper, ProductMapper productMapper) {
        this.productDao = productDao;
        this.productDBMapper = productDBMapper;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional(readOnly = true)
    @RabbitListener(queues = LIST_PRODUCTS_QUEUE)
    public ListProductsRequestResponse fetchAllProducts() {
        ListProductsRequestResponse listProductsRequestResponse;
        ResponseStatus responseStatus = new ResponseStatus();

        try {
            List<ProductDB> productsDB = productDao.findAllProducts();
            List<Product> products = productsDB.stream().map(productDB -> productMapper.apply(productDB))
                    .collect(Collectors.toList());

            responseStatus.setSuccess(true);
            responseStatus.setMessage("Listing products");
            responseStatus.setException(null);
            listProductsRequestResponse = new ListProductsRequestResponse(products, responseStatus);

        } catch (Exception exception) {
            String LIST_PRODUCTS_ERROR_MESSAGE = "Something went wrong while trying to list products: ";
            log.error(LIST_PRODUCTS_ERROR_MESSAGE, exception);
            responseStatus.setSuccess(false);
            responseStatus.setMessage("Something went wrong while trying to list products.");
            responseStatus.setException(exception.getMessage());
            listProductsRequestResponse = new ListProductsRequestResponse(null, responseStatus);
        }

        return listProductsRequestResponse;
    }

    @Override
    @Transactional
    @RabbitListener(queues = ADD_PRODUCTS_QUEUE)
    public ResponseStatus addProduct(Product product) {
        AddProductsRequestResponse addProductsRequestResponse;
        ResponseStatus responseStatus = new ResponseStatus();
        Product createdProduct;

        try {
            ProductDB productDB = productDBMapper.apply(product);
            ProductDB createdProductDB = productDao.addProduct(
                    productDB.getName(),
                    productDB.getDescription(),
                    productDB.getPrice());
            createdProduct = productMapper.apply(createdProductDB);
            String message = "Product " + createdProduct.getName() + " was created.";
            log.info(message);
            responseStatus.setSuccess(true);
            responseStatus.setMessage(message);
            responseStatus.setException(null);
        } catch (Exception exception) {
            String ADD_PRODUCT_ERROR_MESSAGE = "Something went wrong while trying to add a product: ";
            log.error(ADD_PRODUCT_ERROR_MESSAGE, exception);
            responseStatus.setSuccess(false);
            responseStatus.setMessage(ADD_PRODUCT_ERROR_MESSAGE);
            responseStatus.setException(ExceptionUtils.getRootCause(exception).getMessage());
        }

        return responseStatus;
    }
}
