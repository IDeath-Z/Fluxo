package com.fluxo.api_fluxo.repositories.lot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fluxo.api_fluxo.domain.lot.LotOperation;

@Repository
public interface LotOperationRepository extends JpaRepository<LotOperation, Integer> {

}
