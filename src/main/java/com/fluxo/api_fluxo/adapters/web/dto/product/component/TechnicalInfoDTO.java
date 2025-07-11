package com.fluxo.api_fluxo.adapters.web.dto.product.component;

import java.math.BigDecimal;

public final record TechnicalInfoDTO(
                BigDecimal productWeight,
                BigDecimal productLength,
                BigDecimal productWidth,
                BigDecimal productHeight) {
}
