package com.product.command.event;

import com.product.core.entity.ProductEntity;
import com.product.core.event.ProductCreatedEvent;
import com.product.core.service.ProductService;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public record ProductEventsHandler(ProductService productService) {

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalStateException e) {

    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);
        try {
            productService.save(productEntity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
