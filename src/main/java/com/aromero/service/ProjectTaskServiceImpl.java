package com.aromero.service;

import com.aromero.entity.Backlog;
import com.aromero.entity.Project;
import com.aromero.entity.ProjectTask;
import com.aromero.exception.ProjectNotFoundException;
import com.aromero.repository.BacklogRepository;
import com.aromero.repository.ProjectRepository;
import com.aromero.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private ProjectTaskRepository projectTaskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;

    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
        //try {
        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
        //backlogRepository.findByProjectIdentifier(projectIdentifier);
        projectTask.setBacklog(backlog);
        Integer ptSequence = backlog.getPTSequence();
        ptSequence++;
        backlog.setPTSequence(ptSequence);
        projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + ptSequence);
        projectTask.setProjectIdentifier(projectIdentifier);
        if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
            projectTask.setPriority(3);
        }
        if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask);
        //} catch (Exception ex) {
        //    throw new ProjectNotFoundException("Project '" + projectIdentifier + "' not found.");
        //}
    }

    @Override
    public List<ProjectTask> findBacklogProjectTasksByIdentifier(String projectIdentifier, String username) {
        projectService.findProjectByIdentifier(projectIdentifier, username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
    }

    @Override
    public ProjectTask findProjectTaskByProjectSequence(String backlogId, String projectSequence, String username) {
        projectService.findProjectByIdentifier(backlogId, username);

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectSequence);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project task '" + backlogId + "' not found.");
        }
        if (!projectTask.getProjectIdentifier().equals(backlogId)) {
            throw new ProjectNotFoundException("Project task '" + projectSequence +
                    "' does not exist in project '" + backlogId + "'.");
        }
        return projectTask;
    }

    @Override
    public ProjectTask updateProjectTaskByProjectSequence(ProjectTask updatedTask,
                                                          String backlogId, String projectSequence, String username) {
        ProjectTask projectTask = findProjectTaskByProjectSequence(backlogId, projectSequence, username);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    @Override
    public void deleteProjectTaskByProjectSequence(String backlogId, String projectSequence, String username) {
        ProjectTask projectTask = findProjectTaskByProjectSequence(backlogId, projectSequence, username);
        projectTaskRepository.delete(projectTask);
    }
}
