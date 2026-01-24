package com.lovable.lovableClone.mapper;

import com.lovable.lovableClone.dto.auth.AuthResponse;
import com.lovable.lovableClone.dto.auth.SignupRequest;
import com.lovable.lovableClone.dto.auth.UserProfileResponse;
import com.lovable.lovableClone.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity (SignupRequest signupRequest);

    UserProfileResponse toUserProfileResponse(User user);
}
