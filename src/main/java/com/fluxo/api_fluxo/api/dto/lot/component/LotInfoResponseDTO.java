package com.fluxo.api_fluxo.api.dto.lot.component;

import java.time.LocalDate;

public final record LotInfoResponseDTO(
        Integer id,
        SupplierInfoDTO supplierInfo,
        String lotCode,
        LocalDate expiryDate,
        Integer remainingQuantity,
        String lotLocation) {
}
