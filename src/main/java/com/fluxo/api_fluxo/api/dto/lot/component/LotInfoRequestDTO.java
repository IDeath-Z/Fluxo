package com.fluxo.api_fluxo.api.dto.lot.component;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public final record LotInfoRequestDTO(
                @NotBlank String lotCode,
                @NotBlank LocalDate expiryDate,
                String lotLocation) {
}
