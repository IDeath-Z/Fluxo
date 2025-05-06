package com.fluxo.api_fluxo.repositories.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fluxo.api_fluxo.domain.product.PriceInfo;

@Repository
public interface PriceInfoRepository extends JpaRepository<PriceInfo, Integer> {

    List<PriceInfo> findByProductIdIn(List<Integer> productIds);

    Optional<PriceInfo> findByProductId(Integer productId);
}
