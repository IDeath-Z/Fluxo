package com.fluxo.api_fluxo.api.dto.user;

import java.util.List;

import com.fluxo.api_fluxo.api.dto.user.component.UserListDTO;

public record UserListResponseDTO(
                long totalUsers,
                Integer totalPages,
                List<UserListDTO> userList) {
}
