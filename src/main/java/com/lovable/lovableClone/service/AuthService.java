package com.lovable.lovableClone.service;

import com.lovable.lovableClone.dto.auth.AuthResponse;
import com.lovable.lovableClone.dto.auth.LoginRequest;
import com.lovable.lovableClone.dto.auth.SignupRequest;
import org.jspecify.annotations.Nullable;



public interface AuthService {
     AuthResponse signup(SignupRequest request);

     AuthResponse login(LoginRequest request);
}
