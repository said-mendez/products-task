package com.skytouch.microservice.model.repository;

import com.skytouch.microservice.model.ProductDB;
import com.skytouch.microservice.repository.ProductDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class ProductDaoTest {
    @Autowired
    ProductDao productDao;

    @Test
    public void shouldAddANewProduct() {
        // Given:
        ProductDB productDB = new ProductDB();
        productDB.setName(UUID.randomUUID().toString());
        productDB.setDescription("New Product Description");
        BigDecimal price = new BigDecimal("123.123123123");
        price = price.setScale(2, RoundingMode.HALF_EVEN);
        productDB.setPrice(price);

        // When:
        ProductDB insertedProduct = productDao.addProduct(productDB.getName(), productDB.getDescription(), productDB.getPrice());

        // Then:
        assertThat(insertedProduct.getName()).isEqualTo(productDB.getName());
        assertThat(insertedProduct.getDescription()).isEqualTo(productDB.getDescription());
        assertThat(insertedProduct.getPrice()).isEqualTo(productDB.getPrice());
        assertThat(insertedProduct.getId()).isNotNull();
    }

    @Test
    public void tryingToAddProductWithoutRequiredAttributesThrowsInvalidDataAccessResourceUsageException() {
        // Given:
        ProductDB productDB = new ProductDB();
        productDB.setDescription("ABC-123 Description");
        BigDecimal price = new BigDecimal("123.123123123");
        price = price.setScale(2, RoundingMode.HALF_EVEN);
        productDB.setPrice(price);

        // When:
        // Then:
        assertThatThrownBy(() -> productDao.addProduct(productDB.getName(), productDB.getDescription(), productDB.getPrice()))
                .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class)
        ;
    }

    @Test
    public void tryingToAddDuplicateProductThrowsDataIntegrityViolationException() {
        // Given:
        ProductDB productDB = new ProductDB();
        productDB.setName("New Duplicated Product");
        productDB.setDescription("New Duplicated Product Description");
        BigDecimal price = new BigDecimal("666");
        price = price.setScale(2, RoundingMode.HALF_EVEN);
        productDB.setPrice(price);
        productDao.addProduct(productDB.getName(), productDB.getDescription(), productDB.getPrice());

        ProductDB duplicatedProductDB = new ProductDB();
        duplicatedProductDB.setName("New Duplicated Product");
        duplicatedProductDB.setDescription("New Duplicated Product Description");
        duplicatedProductDB.setPrice(price);

        // When:
        // Then:
        assertThatThrownBy(() -> productDao.addProduct(duplicatedProductDB.getName(), duplicatedProductDB.getDescription(), duplicatedProductDB.getPrice()))
                .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class)
        ;
    }

    @Test
    public void returnsAListOfProductsWithCorrectSize() {
        // Given:
        int numberOfProducts = 3;
        for (int i = 1; i <= numberOfProducts; i++) {
            String randomName = UUID.randomUUID().toString();
            ProductDB productDB = new ProductDB();
            productDB.setName("Product " + randomName);
            productDB.setDescription("Product Description " + i);
            BigDecimal price = new BigDecimal("1234");
            price = price.setScale(2, RoundingMode.HALF_EVEN);
            productDB.setPrice(price);
            productDao.addProduct(productDB.getName(), productDB.getDescription(), productDB.getPrice());
        }

        // When:
        List<ProductDB> productsList = productDao.findAllProducts();

        // Then:
        assertThat(productsList.size()).isGreaterThanOrEqualTo(numberOfProducts);
    }
}
