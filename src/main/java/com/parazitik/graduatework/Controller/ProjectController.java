package com.parazitik.graduatework.Controller;

import com.parazitik.graduatework.Entity.Projects;
import com.parazitik.graduatework.Model.ProjectDTO;
import com.parazitik.graduatework.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("create")
    public ResponseEntity<Object> createProject(@RequestBody ProjectDTO projects){
        return projectService.createProject(projects);
    }

    @GetMapping("get-project")
    public ResponseEntity<Object> getProjectById(@RequestParam Long projectId){

        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @PutMapping("update-project")
    public ResponseEntity<Object> updateProjectById(@RequestBody Projects projects){
        return projectService.updateProjectById(projects);
    }

    @GetMapping("get-user-projects")
    public ResponseEntity<List<Projects>> getProjectsByUserId(@RequestParam Long ownerId){

        return projectService.getUserProjects(ownerId);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteProject(@RequestParam Long projectId) {
        return projectService.deleteProject(projectId);
    }
}
