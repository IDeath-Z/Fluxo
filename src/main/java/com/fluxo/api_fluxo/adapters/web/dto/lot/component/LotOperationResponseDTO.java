package com.fluxo.api_fluxo.adapters.web.dto.lot.component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fluxo.api_fluxo.domain.lot.Operations;

public final record LotOperationResponseDTO(
        Integer id,
        LocalDateTime movementDate,
        Integer movementAmount,
        BigDecimal unitaryPrice,
        String notes,
        Operations movementType) {

}
