package com.skytouch.commonlibrary.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product {
    @JsonProperty("id")
    private String id;

    @NotNull
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;
}
