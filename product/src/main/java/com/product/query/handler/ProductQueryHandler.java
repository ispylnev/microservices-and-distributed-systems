package com.product.query.handler;

import com.product.core.entity.ProductEntity;
import com.product.core.service.ProductService;
import com.product.query.model.FindProductQuery;
import com.product.query.model.ProductQueryModel;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductQueryHandler {
    private final ProductService productService;

    @QueryHandler
    public List<ProductQueryModel> findProducts(FindProductQuery findProductQuery) {
        List<ProductQueryModel> products = new ArrayList<>();
        List<ProductEntity> storedProduct = productService.findAll();

        for (ProductEntity entity : storedProduct) {
            ProductQueryModel productQueryModel = new ProductQueryModel();
            BeanUtils.copyProperties(entity, productQueryModel);
            products.add(productQueryModel);
        }
        return products;
    }
}
