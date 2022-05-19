package com.product.command.event;

import com.product.core.entity.ProductEntity;
import com.product.core.event.ProductCreatedEvent;
import com.product.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEventsHandler {

    private final ProductService productService;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);
        productService.save(productEntity);
    }

}
