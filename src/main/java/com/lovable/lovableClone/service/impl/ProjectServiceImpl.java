package com.lovable.lovableClone.service.impl;

import com.lovable.lovableClone.dto.project.ProjectRequest;
import com.lovable.lovableClone.dto.project.ProjectResponse;
import com.lovable.lovableClone.dto.project.ProjectSummaryResponse;
import com.lovable.lovableClone.entity.Project;
import com.lovable.lovableClone.entity.User;
import com.lovable.lovableClone.mapper.ProjectMapper;
import com.lovable.lovableClone.repository.ProjectRepository;
import com.lovable.lovableClone.repository.UserRepository;
import com.lovable.lovableClone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {
//        return projectRepository.findAllAccessibleByUser((userId))
//                .stream()
//                .map(projectMapper::toProjectSummaryResponse)
//                .collect(Collectors.toList());
        List<Project> projects =  projectRepository.findAllAccessibleByUser((userId));
        return projectMapper.toListOfProjectSummaryResponse(projects);

    }

    @Override
    public ProjectResponse getUserProjectById(Long id, Long userId) {
        return null;
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        User owner =  userRepository.findById(userId).orElseThrow();
        Project project = Project.builder()
                .name(request.name())
                .owner(owner)
                .isPublic(false)
                .build();
        project = projectRepository.save(project);
        // now we want to convert project entity to ProjectResponse record dto okay
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        return null;
    }

    @Override
    public void softDelete(Long id, Long userId) {

    }
}
