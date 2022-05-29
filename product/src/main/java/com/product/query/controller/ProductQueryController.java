package com.product.query.controller;

import com.product.query.model.FindProductQuery;
import com.product.query.model.ProductQueryModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductQueryController {
    private final QueryGateway queryGateway;

    @GetMapping("/getAll")
    public List<ProductQueryModel> getAll() {
        FindProductQuery findProductQuery = new FindProductQuery();
        List<ProductQueryModel> products = queryGateway.query(findProductQuery,
                ResponseTypes.multipleInstancesOf(ProductQueryModel.class)).join();
        return products;
    }
}
