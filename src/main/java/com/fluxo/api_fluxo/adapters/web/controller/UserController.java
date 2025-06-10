package com.fluxo.api_fluxo.adapters.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fluxo.api_fluxo.adapters.web.dto.auth.LoginRequestDTO;
import com.fluxo.api_fluxo.adapters.web.dto.auth.LoginResponseDTO;
import com.fluxo.api_fluxo.adapters.web.dto.user.UserListResponseDTO;
import com.fluxo.api_fluxo.adapters.web.dto.user.UserPatchDTO;
import com.fluxo.api_fluxo.adapters.web.dto.user.UserRequestDTO;
import com.fluxo.api_fluxo.adapters.web.dto.user.UserResponseDTO;
import com.fluxo.api_fluxo.application.services.TokenService;
import com.fluxo.api_fluxo.application.services.UserService;
import com.fluxo.api_fluxo.domain.user.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Operações relacionadas a usuários e autenticação")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Realiza o login e retorna um token JWT")
    @ApiResponse(responseCode = "200", description = "Login bem-sucedido")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok(new LoginResponseDTO(tokenService.generateToken((User) auth.getPrincipal())));
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar novo usuário", description = "Cria um novo usuário no sistema")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO userRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userRequest));
    }

    @PatchMapping("/atualizar/{userId}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza campos específicos de um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<?> patchUser(
            @Parameter(description = "ID do usuário") @PathVariable("userId") Integer id,
            @RequestBody UserPatchDTO userPatch) {

        try {
            return ResponseEntity.ok(userService.patchUser(id, userPatch));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os detalhes de um usuário específico")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<?> getUser(
            @Parameter(description = "ID do usuário") @PathVariable("userId") Integer id) {

        try {
            return ResponseEntity.ok(userService.fetchUser(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/todos")
    @Operation(summary = "Listar todos os usuários", description = "Retorna lista paginada de usuários")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<UserListResponseDTO> getAllUsers(
            @Parameter(description = "Número da página (base 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página (padrão 10)") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filtro de pesquisa (opcional)") @RequestParam(required = false) String search) {

        return ResponseEntity.ok(userService.fetchAllUsers(page, size, search));
    }

    @DeleteMapping("/apagar/{userId}")
    @Operation(summary = "Excluir usuário", description = "Remove permanentemente um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "ID do usuário") @PathVariable("userId") Integer id) {

        try {
            userService.removeUser(id);
            return ResponseEntity.ok("Usuário deletado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
