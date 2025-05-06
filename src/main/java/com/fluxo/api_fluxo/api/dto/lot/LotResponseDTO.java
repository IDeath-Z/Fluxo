package com.fluxo.api_fluxo.api.dto.lot;

import com.fluxo.api_fluxo.api.dto.lot.component.LotInfoResponseDTO;
import com.fluxo.api_fluxo.api.dto.lot.component.LotOperationResponseDTO;

public final record LotResponseDTO(
                LotInfoResponseDTO lotInfo,
                LotOperationResponseDTO lotOperation) {

}
