package com.fluxo.api_fluxo.api.dto.product;

import com.fluxo.api_fluxo.api.dto.product.component.PriceInfoDTO;
import com.fluxo.api_fluxo.api.dto.product.component.ProductInfoDTO;
import com.fluxo.api_fluxo.api.dto.product.component.TechnicalInfoDTO;

public final record ProductPatchDTO(
                ProductInfoDTO productInfo,
                PriceInfoDTO priceInfo,
                TechnicalInfoDTO technicalInfo) {
}
