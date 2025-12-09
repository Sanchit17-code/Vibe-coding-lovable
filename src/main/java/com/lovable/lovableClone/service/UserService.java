package com.lovable.lovableClone.service;

import com.lovable.lovableClone.dto.auth.UserProfileResponse;

public interface UserService {
    UserProfileResponse getProfile(Long userId);
}
