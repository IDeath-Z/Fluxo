package com.fluxo.api_fluxo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fluxo.api_fluxo.api.dto.user.UserListResponseDTO;
import com.fluxo.api_fluxo.api.dto.user.UserPatchDTO;
import com.fluxo.api_fluxo.api.dto.user.UserRequestDTO;
import com.fluxo.api_fluxo.api.dto.user.UserResponseDTO;
import com.fluxo.api_fluxo.api.dto.user.component.UserListDTO;
import com.fluxo.api_fluxo.domain.user.User;
import com.fluxo.api_fluxo.repositories.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ---- Método para adicionar um novo usuário ---- //
    public UserResponseDTO addUser(UserRequestDTO request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email já cadastrado: " + request.email());
        }

        User user = saveUser(request);
        return mapToUserDTO(userRepository.save(user));
    }
    // ---- Método para adicionar um novo usuário ---- //

    // ---- Método para atualizar um usuário existente ---- //
    public UserResponseDTO patchUser(Integer userId,
            UserPatchDTO userPatch) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (userPatch.email() != null) {

            user.setEmail(userPatch.email());
        }
        if (userPatch.name() != null) {
            user.setName(userPatch.name());
        }
        if (userPatch.password() != null) {

            user.setPassword(userPatch.password());
        }
        if (userPatch.registrationNumber() != null) {

            user.setRegistrationNumber(userPatch.registrationNumber());
        }
        if (userPatch.department() != null) {
            user.setDepartment(userPatch.department());
        }
        if (userPatch.role() != null) {
            user.setRole(userPatch.role());
        }

        User updatedUser = userRepository.save(user);
        return mapToUserDTO(updatedUser);
    }
    // ---- Método para atualizar um usuário existente ---- //

    // ---- Método para retornar todos os dados do usuário pelo id---- //
    public UserResponseDTO fetchUser(int id) {

        return mapToUserDTO(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: ID " + id)));
    }
    // ---- Método para retornar todos os dados do usuário pelo id ---- //

    // ---- Método para retornar uma lista paginada de usuários ---- //
    public UserListResponseDTO fetchAllUsers(int page, int size) {

        return new UserListResponseDTO(userRepository.count(), userRepository.findAll(PageRequest.of(page, size))
                .map(this::mapToUserListDTO)
                .getContent());
    }
    // ---- Método para retornar uma lista paginada de usuários ---- //

    // ---- Método para remover um usuário ---- //
    public void removeUser(Integer userId) {

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + userId);
        }

        userRepository.deleteById(userId);
    }
    // ---- Método para remover um usuário ---- //

    // ---- Método para criar um usuário no banco a partir da requisição ---- //
    private User saveUser(UserRequestDTO request) {

        User user = new User();
        user.setEmail(request.email());
        user.setName(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRegistrationNumber(request.registrationNumber());
        user.setDepartment(request.department());
        user.setRole(request.role());
        return user;
    }
    // ---- Método para criar um usuário no banco a partir da requisição ---- //

    // ---- Método para mapear os dados de resposta do usuário ---- //
    private UserResponseDTO mapToUserDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRegistrationNumber(),
                user.getRegistrationDate(),
                user.getDepartment(),
                user.getRole());
    }
    // ---- Método para mapear os dados de resposta do usuário ---- //

    // ---- Método para mapear os dados de resposta do usuário no formato de lista ---- //
    private UserListDTO mapToUserListDTO(User user) {
        return new UserListDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole());
    }
    // ---- Método para mapear os dados de resposta do usuário no formato de lista ---- //
}