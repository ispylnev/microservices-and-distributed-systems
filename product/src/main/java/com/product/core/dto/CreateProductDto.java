package com.product.core.dto;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record CreateProductDto(
        @NotBlank(message = "Product title is a required filed")
        String title,
        BigDecimal price,
        Integer quantity
) {
}
