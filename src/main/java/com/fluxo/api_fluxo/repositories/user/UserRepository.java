package com.fluxo.api_fluxo.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.fluxo.api_fluxo.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);

    long count();
}
