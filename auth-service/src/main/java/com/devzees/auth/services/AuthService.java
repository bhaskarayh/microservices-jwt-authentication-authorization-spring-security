package com.devzees.auth.services;

import com.devzees.auth.dtos.JwtResponseDTO;
import com.devzees.auth.models.User;

public interface AuthService {
    String createUser(User user);

    JwtResponseDTO generateToken(String username);

    void validateToken(String jwtToken);
}
