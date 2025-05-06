package com.fluxo.api_fluxo.api.dto.product.component;

public final record ProductInfoDTO(
        String productName,
        String productDescription,
        String productSKU,
        String productCategory,
        String productBrand,
        String productModel) {
}
