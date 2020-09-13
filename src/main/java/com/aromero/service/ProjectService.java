package com.aromero.service;

import com.aromero.entity.Project;

import java.util.List;

public interface ProjectService {

    Project saveOrUpdateProject(Project project, String username);
    Project findProjectByIdentifier(String projectId, String username);
    List<Project> findAllProjects(String username);
    void deleteProjectByIdentifier(String projectId, String username);
}
