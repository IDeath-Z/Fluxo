package com.fluxo.api_fluxo.domain.user;

import lombok.Getter;

@Getter
public enum Roles {
    ADMIN("ADMIN"),
    FUNCIONARIO("FUNCIONARIO");

    private final String role;

    Roles(String role) {
        this.role = role;
    }
}