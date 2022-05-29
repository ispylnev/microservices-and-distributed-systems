package com.product.query.event;

import com.communicationcorelibrary.communicationcorelibrary.event.ProductReservedEvent;
import com.product.core.entity.ProductEntity;
import com.product.core.event.ProductCreatedEvent;
import com.product.query.service.ProductService;
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

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) {
        ProductEntity entity =
                productService.findById(productReservedEvent.getProductId());
        entity.setQuantity(entity.getQuantity() - productReservedEvent.getQuantity());
        try {
            productService.save(entity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
