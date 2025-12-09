package com.lovable.lovableClone.dto.member;

import com.lovable.lovableClone.enums.ProjectRole;

public record InviteMemberRequest(
        String email,
        ProjectRole role
) {
}
