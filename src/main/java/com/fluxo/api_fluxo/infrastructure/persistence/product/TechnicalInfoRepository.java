package com.fluxo.api_fluxo.infrastructure.persistence.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fluxo.api_fluxo.domain.product.TechnicalInfo;

@Repository
public interface TechnicalInfoRepository extends JpaRepository<TechnicalInfo, Integer> {

    List<TechnicalInfo> findByProductIdIn(List<Integer> productIds);

    Optional<TechnicalInfo> findByProductId(Integer productId);
}
