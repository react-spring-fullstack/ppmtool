package com.aromero.service;

import com.aromero.entity.Backlog;
import com.aromero.entity.Project;
import com.aromero.entity.User;
import com.aromero.exception.ProjectIdException;
import com.aromero.exception.ProjectNotFoundException;
import com.aromero.repository.BacklogRepository;
import com.aromero.repository.ProjectRepository;
import com.aromero.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Project saveOrUpdateProject(Project project, String username) {
        if (project.getId() != null) {
            Optional<Project> projectOptional = projectRepository.findById(project.getId());
            Project p = projectOptional.isPresent() ? projectOptional.get() : null;
            if (p != null && !p.getProjectLeader().equals(username)) {
                throw new ProjectNotFoundException("Project not found in your account.");
            } else if (p == null) {
                throw new ProjectNotFoundException("Project ID: `" + project.getId() + "' can not be updated because does not exist.");
            }
        }
        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
                project.setBacklog(backlog);
            } else {
                project.setBacklog(backlogRepository.
                        findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);

        } catch (Exception ex) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier() + "' already exist.");
        }
    }

    @Override
    public Project findProjectByIdentifier(String projectId, String username) {
        Project project = projectRepository.findByProjectIdentifier(projectId);
        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectId + "' does not exist.");
        }
        if (!project.getProjectLeader().equals(username)) {
            throw new ProjectNotFoundException("Project not found in your account.");
        }
        return project;
    }

    @Override
    public List<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    @Override
    public void deleteProjectByIdentifier(String projectId, String username) {
        Project project = findProjectByIdentifier(projectId, username);
        projectRepository.delete(project);
    }
}
