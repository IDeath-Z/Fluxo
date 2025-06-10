package com.fluxo.api_fluxo.adapters.web.dto.lot.component;

import java.math.BigDecimal;

import com.fluxo.api_fluxo.domain.lot.Operations;

import jakarta.validation.constraints.NotBlank;

public final record LotOperationRequestDTO(
        @NotBlank Integer movementAmount,
        @NotBlank BigDecimal unitPrice,
        String notes,
        @NotBlank Operations movementType) {

}
