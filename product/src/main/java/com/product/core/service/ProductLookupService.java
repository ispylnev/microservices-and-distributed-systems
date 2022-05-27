package com.product.core.service;

import com.product.core.entity.ProductLookupEntity;
import com.product.core.repository.ProductLookupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductLookupService {

    private final ProductLookupRepository productLookupRepository;

    public ProductLookupEntity findProductLookupEntityByProductionIdOrTitle(String productId, String title) {
        return productLookupRepository.findProductLookupEntityByProductionIdOrTitle(productId, title);
    }

    public void save(ProductLookupEntity productLookupEntity) {
        productLookupRepository.save(productLookupEntity);
    }

}
