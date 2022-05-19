package com.clients.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "product",
        url = "${clients.product.url}"
)
@Service
public interface ProductClient {
    @PostMapping("api/v1/product")
    void createProduct(@RequestBody CreateProductRequest createProductRequest);
}
