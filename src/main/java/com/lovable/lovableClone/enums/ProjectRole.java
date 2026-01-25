package com.lovable.lovableClone.enums;

import com.lovable.lovableClone.entity.Project;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.lovable.lovableClone.enums.ProjectPermission.*;

@RequiredArgsConstructor
@Getter
public enum ProjectRole {
    EDITOR(Set.of(VIEW,EDIT, DELETE, VIEW_MEMBERS)),
    VIEWER(Set.of(VIEW, VIEW_MEMBERS)),
    OWNER(Set.of(VIEW,EDIT,DELETE,MANAGE_MEMBERS, VIEW_MEMBERS));

    ProjectRole(ProjectPermission... permissions){
        this.permissions = Set.of(permissions);
    }
    private final Set<ProjectPermission> permissions;
}
