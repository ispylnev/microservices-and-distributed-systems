package com.product.query.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductQueryModel {
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
