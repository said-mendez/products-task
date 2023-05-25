package com.skytouch.microservice.repository;

import com.skytouch.microservice.model.ProductDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<ProductDB, String> {

    @Query(value = "CALL read_products();", nativeQuery = true)
    List<ProductDB> findAllProducts();

    @Query(value = "CALL insert_product(:name_in, :description_in, :price_in);", nativeQuery = true)
    ProductDB addProduct (@Param("name_in") String name_in,
                          @Param("description_in") String description_in,
                          @Param("price_in") BigDecimal price_in);
}