package com.fluxo.api_fluxo.api.dto.product.component;

import java.math.BigDecimal;

public final record PriceInfoDTO(
        BigDecimal productPrice,
        BigDecimal promotionalPrice) {
}
