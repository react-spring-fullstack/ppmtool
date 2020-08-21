package com.aromero.service;

import com.aromero.entity.Project;
import com.aromero.exception.ProjectIdException;
import com.aromero.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier() + "' already exist.");
        }
    }

    @Override
    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId);
        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectId + "' does not exist.");
        }
        return project;
    }

    @Override
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void deleteProjectByIdentifier(String projectId) {
        Project project = findProjectByIdentifier(projectId);
        projectRepository.delete(project);
    }
}
