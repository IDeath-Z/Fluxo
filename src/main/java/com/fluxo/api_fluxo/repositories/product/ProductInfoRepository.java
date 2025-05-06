package com.fluxo.api_fluxo.repositories.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fluxo.api_fluxo.domain.product.ProductInfo;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Integer> {

    Page<ProductInfo> findByProductNameContainingIgnoreCaseOrProductSKUContainingIgnoreCase(
            String productName,
            String productSKU,
            Pageable pageable);

    @Query("SELECT DISTINCT p.productCategory FROM ProductInfo p")
    List<String> findAllCategories();
}
