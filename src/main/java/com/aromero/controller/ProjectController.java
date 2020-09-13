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
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ValidationErrorService validationErrorService;

    @PostMapping
    public ResponseEntity<?> createNewProject(@Valid @RequestBody  Project project,
                                              BindingResult result, Principal principal) {
        ResponseEntity<?> responseEntity = validationErrorService.mapValidationError(result);
        if (responseEntity != null) return responseEntity;

        Project saveOrUpdateProject = projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<>(saveOrUpdateProject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String id, Principal principal) {
        Project project = projectService.findProjectByIdentifier(id, principal.getName());
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects(Principal principal) {
        List<Project> projects = projectService.findAllProjects(principal.getName());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id, Principal principal) {
        projectService.deleteProjectByIdentifier(id, principal.getName());
        return ResponseEntity.ok().build();
    }
}
