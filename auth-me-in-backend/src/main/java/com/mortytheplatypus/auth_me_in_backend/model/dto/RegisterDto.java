package com.mortytheplatypus.auth_me_in_backend.model.dto;

import com.mortytheplatypus.auth_me_in_backend.model.Role;

public record RegisterDto(String name, String email, String username, String password, Role role) {
}
