package com.skytouch.microservice.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ListProductsRequestResponse {
    private Map<String, List<ProductDB>> responseData;
    private Map<String, ResponseStatus> responseStatus;

    public ListProductsRequestResponse(List<ProductDB> responseData, ResponseStatus responseStatus) {
        this.responseData = new HashMap<>();
        this.responseStatus = new HashMap<>();
        this.responseData.put("data", responseData);
        this.responseStatus.put("status", responseStatus);
    }
}
