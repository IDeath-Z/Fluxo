package com.fluxo.api_fluxo.adapters.web.dto.user;

import com.fluxo.api_fluxo.domain.user.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public final record UserRequestDTO(
                @NotBlank @Email String email,
                @NotBlank String name,
                @NotBlank String password,
                @NotBlank String registrationNumber,
                @NotBlank String department,
                @NotBlank Roles role) {
}
