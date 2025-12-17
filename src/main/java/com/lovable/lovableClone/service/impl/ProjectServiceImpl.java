package com.lovable.lovableClone.service.impl;

import com.lovable.lovableClone.dto.project.ProjectRequest;
import com.lovable.lovableClone.dto.project.ProjectResponse;
import com.lovable.lovableClone.dto.project.ProjectSummaryResponse;
import com.lovable.lovableClone.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProjectServiceImpl implements ProjectService {
    @Override
    public Optional<List<ProjectSummaryResponse>> getUserProjects(Long userId) {
        return Optional.empty();
    }

    @Override
    public ProjectResponse getUserProjectById(Long id, Long userId) {
        return null;
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        return null;
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        return null;
    }

    @Override
    public void softDelete(Long id, Long userId) {

    }
}
