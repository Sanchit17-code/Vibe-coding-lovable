package com.lovable.lovableClone.repository;

import com.lovable.lovableClone.entity.ProjectMember;
import com.lovable.lovableClone.entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findByIdProjectId(Long ProjectId);
}
