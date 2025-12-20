package com.lovable.lovableClone.mapper;

import com.lovable.lovableClone.dto.project.ProjectResponse;
import com.lovable.lovableClone.dto.project.ProjectSummaryResponse;
import com.lovable.lovableClone.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse toProjectResponse(Project project);

    @Mapping(source = "name", target = "projectName")
    ProjectSummaryResponse toProjectSummaryResponse(Project project);

    List<ProjectSummaryResponse> toListOfProjectSummaryResponse(List<Project> projects);

}
