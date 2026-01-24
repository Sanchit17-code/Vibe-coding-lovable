package com.lovable.lovableClone.service;

import com.lovable.lovableClone.dto.project.ProjectRequest;
import com.lovable.lovableClone.dto.project.ProjectResponse;
import com.lovable.lovableClone.dto.project.ProjectSummaryResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<ProjectSummaryResponse> getUserProjects();

    ProjectResponse getUserProjectById(Long id);

    ProjectResponse createProject(ProjectRequest request);

    ProjectResponse updateProject(Long id, ProjectRequest request);

    void softDelete(Long id);
}
