package com.mortytheplatypus.auth_me_in_backend.repository;

import com.mortytheplatypus.auth_me_in_backend.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Override
    @NotNull
    Optional<User> findById(@NotNull UUID id);

    Optional<User> findByUsername(String username);
}
