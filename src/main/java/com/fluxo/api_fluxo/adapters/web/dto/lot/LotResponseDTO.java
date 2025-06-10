package com.fluxo.api_fluxo.adapters.web.dto.lot;

import com.fluxo.api_fluxo.adapters.web.dto.lot.component.LotInfoResponseDTO;
import com.fluxo.api_fluxo.adapters.web.dto.lot.component.LotOperationResponseDTO;

public final record LotResponseDTO(
        LotInfoResponseDTO lotInfo,
        LotOperationResponseDTO lotOperation) {

}
