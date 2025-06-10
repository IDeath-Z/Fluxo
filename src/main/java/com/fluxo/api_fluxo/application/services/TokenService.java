package com.fluxo.api_fluxo.application.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fluxo.api_fluxo.domain.user.User;

@Service
public class TokenService {

    // ---- Variável definida no application.properties ---- //
    @Value("${api.security.token.secret}")
    private String secret;
    // ---- Variável definida no application.properties ---- //

    // ---- Método para gerar o barrier token para autenticação ---- //
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("api_fluxo")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token", exception);
        }
    }
    // ---- Método para gerar o barrier token para autenticação ---- //

    // ---- Método para validar se o token foi gerado pelo sistema ---- //
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("api_fluxo")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }
    // ---- Método para validar se o token foi gerado pelo sistema ---- //

    // ---- Método para definir o tempo de validade do token ---- //
    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.of("-03:00"));
    }
    // ---- Método para definir o tempo de validade do token ---- //
}
