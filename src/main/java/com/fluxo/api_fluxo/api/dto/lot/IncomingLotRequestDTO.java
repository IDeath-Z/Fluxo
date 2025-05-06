package com.fluxo.api_fluxo.api.dto.lot;

import com.fluxo.api_fluxo.api.dto.lot.component.*;

public final record IncomingLotRequestDTO(
                Integer productId,
                SupplierInfoDTO supplierInfo,
                LotInfoRequestDTO lotInfo,
                LotOperationRequestDTO lotOperation) {
}
