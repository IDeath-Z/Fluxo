package com.fluxo.api_fluxo.adapters.web.dto.lot;

import com.fluxo.api_fluxo.adapters.web.dto.lot.component.*;

public final record OutgoingLotRequestDTO(
                Integer productId,
                LotOperationRequestDTO lotOperation) {
}
