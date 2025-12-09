package com.lovable.lovableClone.dto.auth;

public record SignupRequest(
        String email,
        String name,
        String password
) {
}
