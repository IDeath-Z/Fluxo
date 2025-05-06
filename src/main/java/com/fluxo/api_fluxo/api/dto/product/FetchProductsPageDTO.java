package com.fluxo.api_fluxo.api.dto.product;

import java.util.List;

import com.fluxo.api_fluxo.api.dto.product.component.FetchProductsDTO;

public record FetchProductsPageDTO(
        Integer totalPages,
        List<FetchProductsDTO> productsInPage) {
}
