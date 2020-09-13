package com.aromero.service;

import com.aromero.entity.ProjectTask;

import java.util.List;

public interface ProjectTaskService {
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username);
    public List<ProjectTask> findBacklogProjectTasksByIdentifier(String projectIdentifier, String username);
    public ProjectTask findProjectTaskByProjectSequence(String backlogId, String projectSequence, String username);
    public ProjectTask updateProjectTaskByProjectSequence(ProjectTask updatedTask,
                                                          String backlogId, String projectSequence, String username);
    public void deleteProjectTaskByProjectSequence(String backlogId, String projectSequence, String username);
}
