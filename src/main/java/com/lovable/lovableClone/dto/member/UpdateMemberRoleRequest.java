package com.lovable.lovableClone.dto.member;

import com.lovable.lovableClone.enums.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(
        @NotNull ProjectRole role) {
}
