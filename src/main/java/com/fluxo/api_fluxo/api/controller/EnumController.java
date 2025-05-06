package com.fluxo.api_fluxo.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fluxo.api_fluxo.api.dto.enums.EnumResponseDTO;
import com.fluxo.api_fluxo.domain.lot.Operations;
import com.fluxo.api_fluxo.domain.user.Roles;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping("/enums")
@Tag(name = "Enums", description = "Endpoints para listagem de valores enumerados do sistema")
public class EnumController {

    @GetMapping("/roles")
    @Operation(summary = "Listar papéis de usuário", description = "Retorna todos os tipos de permissões de usuário disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de roles retornada com sucesso")
    public List<EnumResponseDTO> getRoles() {
        return Arrays.stream(Roles.values())
                .map(role -> new EnumResponseDTO(role.name(), role.getRole()))
                .collect(Collectors.toList());
    }

    @GetMapping("/operations")
    @Operation(summary = "Listar tipos de operação", description = "Retorna todos os tipos de movimentações de estoque disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de operações retornada com sucesso")
    public List<EnumResponseDTO> getOperations() {
        return Arrays.stream(Operations.values())
                .map(operation -> new EnumResponseDTO(operation.name(), operation.getOperations()))
                .collect(Collectors.toList());
    }
}
