package com.aromero.service;

import com.aromero.entity.Project;

import java.util.List;

public interface ProjectService {

    Project saveOrUpdateProject(Project project);
    Project findProjectByIdentifier(String projectId);
    List<Project> findAllProjects();
    void deleteProjectByIdentifier(String projectId);
}
