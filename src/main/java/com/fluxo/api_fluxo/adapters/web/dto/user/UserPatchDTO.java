package com.fluxo.api_fluxo.adapters.web.dto.user;

import com.fluxo.api_fluxo.domain.user.Roles;

import jakarta.validation.constraints.Email;

public final record UserPatchDTO(
        @Email String email,
        String name,
        String password,
        String registrationNumber,
        String department,
        Roles role) {
}
