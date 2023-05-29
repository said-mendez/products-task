package com.skytouch.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseStatus {
    private Boolean success;
    private String message;
    private String exception;
}
