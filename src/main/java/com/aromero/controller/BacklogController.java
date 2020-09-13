package com.aromero.controller;

import com.aromero.entity.ProjectTask;
import com.aromero.service.ProjectTaskService;
import com.aromero.service.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;
    @Autowired
    private ValidationErrorService validationErrorService;

    @PostMapping("/{id}")
    public ResponseEntity<?> addPtToBacklog(@PathVariable String id,
                                            @Valid @RequestBody ProjectTask projectTask, BindingResult result, Principal principal) {

        ResponseEntity<?> responseEntity = validationErrorService.mapValidationError(result);
        if (responseEntity != null) return responseEntity;
        ProjectTask task = projectTaskService.addProjectTask(id, projectTask, principal.getName());
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBacklogProjectTasks(@PathVariable String id, Principal principal) {
        return new ResponseEntity<>(projectTaskService.
                findBacklogProjectTasksByIdentifier(id, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{id}/pt/{ptId}")
    public ResponseEntity<?> getProjectTask(@PathVariable String id, @PathVariable String ptId, Principal principal) {
        ProjectTask task = projectTaskService.findProjectTaskByProjectSequence(id, ptId, principal.getName());
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PatchMapping("/{id}/pt/{ptId}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                               @PathVariable String id, @PathVariable String ptId, Principal principal) {
        ResponseEntity<?> responseEntity = validationErrorService.mapValidationError(result);
        if (responseEntity != null) return responseEntity;
        ProjectTask task = projectTaskService.updateProjectTaskByProjectSequence(projectTask, id, ptId, principal.getName());
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/pt/{ptId}")
    public ResponseEntity<?> updateProjectTask(@PathVariable String id, @PathVariable String ptId, Principal principal) {
        projectTaskService.deleteProjectTaskByProjectSequence(id, ptId, principal.getName());
        return new ResponseEntity<String>("Project Task " + ptId + " was deleted successfully", HttpStatus.OK);
    }
}
