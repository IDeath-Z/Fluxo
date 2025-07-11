package com.fluxo.api_fluxo.adapters.web.dto.user.component;

import com.fluxo.api_fluxo.domain.user.Roles;

public record UserListDTO(
        Integer id,
        String name,
        String email,
        Roles role) {

}
