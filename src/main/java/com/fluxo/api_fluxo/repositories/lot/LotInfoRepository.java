package com.fluxo.api_fluxo.repositories.lot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fluxo.api_fluxo.domain.lot.LotInfo;

@Repository
public interface LotInfoRepository extends JpaRepository<LotInfo, Integer> {

    List<LotInfo> findByProductIdIn(List<Integer> productIds);

    List<LotInfo> findByProductId(Integer productId);

    LotInfo findByProduct_Id(Integer productId);
}
