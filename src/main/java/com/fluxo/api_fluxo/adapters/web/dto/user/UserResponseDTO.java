package com.fluxo.api_fluxo.adapters.web.dto.user;

import java.time.LocalDateTime;

import com.fluxo.api_fluxo.domain.user.Roles;

public final record UserResponseDTO(
                Integer id,
                String email,
                String name,
                String registrationNumber,
                LocalDateTime registrationDate,
                String department,
                Roles role) {
}
