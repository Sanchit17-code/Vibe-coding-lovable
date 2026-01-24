package com.lovable.lovableClone.mapper;

import com.lovable.lovableClone.dto.member.MemberResponse;
import com.lovable.lovableClone.entity.ProjectMember;
import com.lovable.lovableClone.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "projectRole", constant = "OWNER")
    MemberResponse toProjectMemberResponseFromOwner(User owner);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "name", source = "user.name")
    MemberResponse toProjectMemberResponseFromMember(ProjectMember member);


    List<MemberResponse> toProjectMemberResponseListFromMemberList (List<ProjectMember> memberList);
}
