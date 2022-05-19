package com.clients.product;

import java.math.BigDecimal;


public record CreateProductRequest(String title, BigDecimal price, Integer quantity) {

}
