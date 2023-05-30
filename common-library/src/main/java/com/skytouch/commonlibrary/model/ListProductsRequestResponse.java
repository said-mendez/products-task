package com.skytouch.commonlibrary.model;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ListProductsRequestResponse {
    private List<Product> data;
    private Map<String, ResponseStatus> responseStatus;

    public ListProductsRequestResponse(List<Product> responseData, ResponseStatus responseStatus) {
        this.responseStatus = new HashMap<>();
        this.data = responseData;
        this.responseStatus.put("status", responseStatus);
    }

    public ListProductsRequestResponse() {}
}
