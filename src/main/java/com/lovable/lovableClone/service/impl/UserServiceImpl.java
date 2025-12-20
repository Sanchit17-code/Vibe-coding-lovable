package com.lovable.lovableClone.service.impl;

import com.lovable.lovableClone.dto.auth.UserProfileResponse;
import com.lovable.lovableClone.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserProfileResponse getProfile(Long userId) {
        return null;
    }
}
