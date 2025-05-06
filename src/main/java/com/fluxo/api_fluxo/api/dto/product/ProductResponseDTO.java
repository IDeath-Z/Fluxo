package com.fluxo.api_fluxo.api.dto.product;

import java.util.List;

import com.fluxo.api_fluxo.api.dto.lot.component.LotInfoResponseDTO;
import com.fluxo.api_fluxo.api.dto.product.component.PriceInfoDTO;
import com.fluxo.api_fluxo.api.dto.product.component.ProductInfoDTO;
import com.fluxo.api_fluxo.api.dto.product.component.TechnicalInfoDTO;

public final record ProductResponseDTO(
                Integer id,
                ProductInfoDTO productInfo,
                PriceInfoDTO priceInfo,
                TechnicalInfoDTO technicalInfo,
                List<LotInfoResponseDTO> lots) {
}
