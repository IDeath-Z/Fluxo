package com.fluxo.api_fluxo.adapters.web.dto.user;

import java.util.List;

import com.fluxo.api_fluxo.adapters.web.dto.user.component.UserListDTO;

public record UserListResponseDTO(
        long totalUsers,
        Integer totalPages,
        List<UserListDTO> userList) {
}
