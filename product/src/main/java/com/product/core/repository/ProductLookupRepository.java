package com.product.core.repository;

import com.product.core.entity.ProductLookupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLookupRepository extends JpaRepository<ProductLookupEntity, String> {

    ProductLookupEntity findProductLookupEntityByProductionIdOrTitle(String productId, String title);
}
