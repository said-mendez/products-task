package com.skytouch.commonlibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseStatus {
    private Boolean success;
    private String message;
    private String exception;
}
