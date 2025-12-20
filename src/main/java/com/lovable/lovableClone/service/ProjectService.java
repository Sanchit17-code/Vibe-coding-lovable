package com.lovable.lovableClone.service;

import com.lovable.lovableClone.dto.project.ProjectRequest;
import com.lovable.lovableClone.dto.project.ProjectResponse;
import com.lovable.lovableClone.dto.project.ProjectSummaryResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<ProjectSummaryResponse> getUserProjects(Long userId);

    ProjectResponse getUserProjectById(Long id, Long userId);

    ProjectResponse createProject(ProjectRequest request, Long userId);

    ProjectResponse updateProject(Long id, ProjectRequest request, Long userId);

    void softDelete(Long id, Long userId);
}
