package com.fluxo.api_fluxo.adapters.web.dto.product.component;

import java.math.BigDecimal;

public final record PriceInfoDTO(
                BigDecimal productPrice,
                BigDecimal promotionalPrice) {
}
