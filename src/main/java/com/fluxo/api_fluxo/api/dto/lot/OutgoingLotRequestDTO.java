package com.fluxo.api_fluxo.api.dto.lot;

import com.fluxo.api_fluxo.api.dto.lot.component.*;

public final record OutgoingLotRequestDTO(
        Integer productId,
        LotOperationRequestDTO lotOperation) {
}
