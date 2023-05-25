package com.skytouch.microservice.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AddProductsRequestResponse {
    private Map<String, ProductDB> responseData;
    private Map<String, ResponseStatus> responseStatus;

    public AddProductsRequestResponse(ProductDB responseData, ResponseStatus responseStatus) {
        this.responseData = new HashMap<>();
        this.responseStatus = new HashMap<>();
        this.responseData.put("data", responseData);
        this.responseStatus.put("status", responseStatus);
    }
}
