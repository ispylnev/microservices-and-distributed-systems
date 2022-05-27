package com.product.command.event;

import com.product.core.entity.ProductLookupEntity;
import com.product.core.event.ProductCreatedEvent;
import com.product.core.service.ProductLookupService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public record ProductLookupEventsHandler(ProductLookupService productLookupService) {

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductLookupEntity product = new ProductLookupEntity(event.getProductId(), event.getTitle());
        productLookupService.save(product);
    }

}
