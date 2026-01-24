package com.lovable.lovableClone.service.impl;

import com.lovable.lovableClone.dto.auth.AuthResponse;
import com.lovable.lovableClone.dto.auth.LoginRequest;
import com.lovable.lovableClone.dto.auth.SignupRequest;
import com.lovable.lovableClone.entity.User;
import com.lovable.lovableClone.error.BadRequestException;
import com.lovable.lovableClone.mapper.UserMapper;
import com.lovable.lovableClone.repository.UserRepository;
import com.lovable.lovableClone.security.AuthUtil;
import com.lovable.lovableClone.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    AuthUtil authUtil;
    AuthenticationManager authenticationManager;
    @Override
    public AuthResponse signup(SignupRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(
                user ->{
                    throw new BadRequestException("User already exists with username : " + request.username());
                }
        );
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user = userRepository.save(user);
        String token = authUtil.generateAccessToken(user);
        return new AuthResponse(
                token,
                userMapper.toUserProfileResponse(user)
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(),request.password())
        );
        User user = (User) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(user);
        return new AuthResponse(token,userMapper.toUserProfileResponse(user));
    }
}
