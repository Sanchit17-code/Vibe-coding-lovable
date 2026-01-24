package com.lovable.lovableClone.service;

import com.lovable.lovableClone.dto.member.InviteMemberRequest;
import com.lovable.lovableClone.dto.member.MemberResponse;
import com.lovable.lovableClone.dto.member.UpdateMemberRoleRequest;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProjectMemberService {

    List<MemberResponse> getProjectMembers(Long projectId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request);

    MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request);

    void removeProjectMember(Long projectId, Long memberId);
}
