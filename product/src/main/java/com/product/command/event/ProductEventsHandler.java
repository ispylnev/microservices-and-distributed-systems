package com.product.command.event;

import com.product.core.entity.ProductEntity;
import com.product.core.event.ProductCreatedEvent;
import com.product.core.service.ProductService;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public record ProductEventsHandler(ProductService productService) {

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);
        productService.save(productEntity);
    }

}
