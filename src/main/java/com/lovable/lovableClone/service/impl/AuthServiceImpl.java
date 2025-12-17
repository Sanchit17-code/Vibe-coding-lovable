package com.lovable.lovableClone.service.impl;

import com.lovable.lovableClone.dto.auth.AuthResponse;
import com.lovable.lovableClone.dto.auth.SignupRequest;
import com.lovable.lovableClone.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public AuthResponse signup(SignupRequest request) {
        return null;
    }

    @Override
    public AuthResponse login() {
        return null;
    }
}
