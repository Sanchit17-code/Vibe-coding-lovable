package com.lovable.lovableClone.service.impl;

import com.lovable.lovableClone.dto.member.InviteMemberRequest;
import com.lovable.lovableClone.dto.member.MemberResponse;
import com.lovable.lovableClone.dto.member.UpdateMemberRoleRequest;
import com.lovable.lovableClone.entity.Project;
import com.lovable.lovableClone.entity.ProjectMember;
import com.lovable.lovableClone.entity.ProjectMemberId;
import com.lovable.lovableClone.entity.User;
import com.lovable.lovableClone.mapper.ProjectMemberMapper;
import com.lovable.lovableClone.repository.ProjectMemberRepository;
import com.lovable.lovableClone.repository.ProjectRepository;
import com.lovable.lovableClone.repository.UserRepository;
import com.lovable.lovableClone.security.AuthUtil;
import com.lovable.lovableClone.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectMemberRepository projectMemberRepository;
    ProjectRepository projectRepository;
    ProjectMemberMapper projectMemberMapper;
    UserRepository userRepository;
    AuthUtil authUtil;

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        //        another way to practise
            //        memberResponseList.addAll( projectMemberRepository.findByIdProjectId(projectId)
            //        .stream()
            //        .map(projectMemberMapper::toProjectMemberResponseFromMember)
            //        .toList());


        return projectMemberMapper.toProjectMemberResponseListFromMemberList( projectMemberRepository.findByIdProjectId(projectId));
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);
        User invitee = userRepository.findByUsername(request.username()).orElseThrow(()-> new RuntimeException("user does not exist"));


        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,invitee.getId());
        // check if the invite has already been sent
        if(projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("already invited");
        }
        ProjectMember projectMember = ProjectMember
                .builder()
                .id(projectMemberId)
                .user(invitee)
                .project(project)
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .build();

        projectMemberRepository.save(projectMember);
        return projectMemberMapper.toProjectMemberResponseFromMember(projectMember);
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,memberId);
        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId).orElseThrow();
        projectMember.setProjectRole(request.role());
        projectMemberRepository.save(projectMember);
        return projectMemberMapper.toProjectMemberResponseFromMember(projectMember);
    }

    @Override
    public void removeProjectMember(Long projectId, Long memberId) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,memberId);
        if(!projectMemberRepository.existsById(projectMemberId)){
           throw new RuntimeException("the member does not exist");
        }
        projectMemberRepository.deleteById(projectMemberId);
    }

    ///  Internal functions

    public Project getAccessibleProjectById(Long projectId, Long userId){
        return projectRepository.findAccessibleProjectById(projectId,userId).orElseThrow();
    }
}
