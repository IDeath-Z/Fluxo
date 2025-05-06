package com.fluxo.api_fluxo.domain.lot;

import lombok.Getter;

@Getter
public enum Operations {

    ENTRADA("ENTRADA"),
    SAIDA("SAIDA"),
    RESERVA("RESERVA");

    private String operations;

    Operations(String operations) {
        this.operations = operations;
    }

}
