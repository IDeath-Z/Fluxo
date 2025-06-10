package com.fluxo.api_fluxo.infrastructure.persistence.lot;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fluxo.api_fluxo.domain.lot.SupplierInfo;

@Repository
public interface SupplierInfoRepository extends JpaRepository<SupplierInfo, Integer> {

    Optional<SupplierInfo> findBySupplierName(String supplierName);
}
