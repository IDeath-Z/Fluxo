package com.fluxo.api_fluxo.api.dto.product.component;

import java.math.BigDecimal;
import java.time.LocalDate;

public final record FetchProductsDTO(
        Integer id,
        String productName,
        String productSKU,
        BigDecimal productPrice,
        Integer availableQuantity,
        LocalDate expiryDate) {

}
