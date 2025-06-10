package com.fluxo.api_fluxo.adapters.web.dto.product;

import com.fluxo.api_fluxo.adapters.web.dto.product.component.PriceInfoDTO;
import com.fluxo.api_fluxo.adapters.web.dto.product.component.ProductInfoDTO;
import com.fluxo.api_fluxo.adapters.web.dto.product.component.TechnicalInfoDTO;

import jakarta.validation.constraints.NotBlank;

public final record ProductRequestDTO(
                @NotBlank ProductInfoDTO productInfo,
                @NotBlank PriceInfoDTO priceInfo,
                TechnicalInfoDTO technicalInfo) {
}
