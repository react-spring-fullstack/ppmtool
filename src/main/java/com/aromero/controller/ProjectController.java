package com.aromero.controller;

import com.aromero.entity.Project;
import com.aromero.service.ProjectService;
import com.aromero.service.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationErrorService validationErrorService;

    @PostMapping
    public ResponseEntity<?> createNewProject(@Valid @RequestBody  Project project, BindingResult result) {
        ResponseEntity<?> responseEntity = validationErrorService.mapValidationError(result);
        if (responseEntity != null) return responseEntity;

        Project saveOrUpdateProject = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(saveOrUpdateProject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String id) {
        Project project = projectService.findProjectByIdentifier(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects() {
        List<Project> projects = projectService.findAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id) {
        projectService.deleteProjectByIdentifier(id);
        return ResponseEntity.ok().build();
    }
}
