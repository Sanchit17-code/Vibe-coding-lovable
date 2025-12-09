package com.lovable.lovableClone.dto.auth;

public record LoginRequest
    (
        String email,
        String password
    )
{
}
