package com.aromero.repository;

import com.aromero.entity.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);
    ProjectTask findByProjectSequence(String projectSequence);
}
