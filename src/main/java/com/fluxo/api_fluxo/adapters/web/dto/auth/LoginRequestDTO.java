package com.fluxo.api_fluxo.adapters.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public final record LoginRequestDTO(
        @NotBlank @Email String email,
        @NotBlank String password) {
}
