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

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {

        List<MemberResponse> memberResponseList = new ArrayList<>();
        Project project = getAccessibleProjectById(projectId, userId);
        MemberResponse owner = projectMemberMapper.toProjectMemberResponseFromOwner(project.getOwner());
        memberResponseList.add(owner);

        List<MemberResponse> membersList =
                projectMemberMapper.toProjectMemberResponseListFromMemberList( projectMemberRepository.findByIdProjectId(projectId));

        memberResponseList.addAll(membersList);

        // another way to practise
//        memberResponseList.addAll( projectMemberRepository.findByIdProjectId(projectId)
//                .stream()
//                .map(projectMemberMapper::toProjectMemberResponseFromMember)
//                .toList());


        return memberResponseList;
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);
        User invitee = userRepository.findByEmail(request.email()).orElseThrow(()-> new RuntimeException("user does not exist"));

        // if userId is not the owner then dont allow as only owner can send invites
        if(!project.getOwner().getId().equals(userId)){
            throw new RuntimeException("Only the owner can send invite");
        }

        // check if the invitee is the owner himself
        if(project.getOwner().getId().equals(invitee.getId())){
            throw new RuntimeException("cannot invite the owner");
        }
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
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);
        if(!project.getOwner().getId().equals(userId)){
            throw new RuntimeException("only owner is allowed to modify the role");
        }
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,memberId);
        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId).orElseThrow();
        projectMember.setProjectRole(request.role());
        projectMemberRepository.save(projectMember);
        return projectMemberMapper.toProjectMemberResponseFromMember(projectMember);
    }

    @Override
    public void removeProjectMember(Long projectId, Long memberId, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);
        if(!project.getOwner().getId().equals(userId)){
            throw new RuntimeException("only owner is allowed to modify the role");
        }
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
