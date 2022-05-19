package com.product.core.service;

import com.product.core.entity.ProductEntity;
import com.product.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductEntity findById(String productId) {
        return productRepository.findByProductId(productId);
    }

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void save(ProductEntity product) {
        productRepository.save(product);
    }
}
