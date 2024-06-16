package com.parazitik.graduatework.Service;

import com.parazitik.graduatework.Entity.Projects;
import com.parazitik.graduatework.Entity.Users;
import com.parazitik.graduatework.Model.MemberDTO;
import com.parazitik.graduatework.Model.ProjectDTO;
import com.parazitik.graduatework.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatisticService statisticService;

    public ResponseEntity<Object> createProject(ProjectDTO projects) {

        Projects projectEntity = new Projects();

        if (projects.getOwnerId() == null || userRepository.findById(projects.getOwnerId()).isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        if (projectEntity.getMembers() == null) {
            projectEntity.setMembers(new ArrayList<>());
        }

        for (MemberDTO member : projects.getMembers()) {
            Users user = userRepository.findById(member.getId()).orElse(null);
            if (user != null && !projectEntity.getMembers().contains(user)) {
                projectEntity.getMembers().add(user);
            }
        }

        if (projects.getName() != null) projectEntity.setName(projects.getName());
        if (projects.getDescription() != null) projectEntity.setDescription(projects.getDescription());
        if (projects.getPriority() != null) projectEntity.setPriority(projects.getPriority());
        if (projects.getTag() != null) projectEntity.setTag(projects.getTag());
        if (projects.getStatus() != null) projectEntity.setStatus(projects.getStatus());
        if (projects.getTagColor() != null) projectEntity.setTagColor(projects.getTagColor());
        if (projects.getOwnerId() != null) projectEntity.setOwnerId(projects.getOwnerId());

        projectRepository.save(projectEntity);

        statisticService.createProject(projects.getOwnerId(), projects.getMembers().size() == 1);

        return ResponseEntity.ok().build();
    }


    public ResponseEntity<List<Projects>> getUserProjects(Long id) {
        return ResponseEntity.ok(projectRepository.findAllProjectsByMemberIdOrOwnerId(id));
    }

    public ResponseEntity<Projects> getProjectById(Long projectId) {
        Projects projects = projectRepository.findById(projectId).orElse(null);

        if (projects == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(projects);
    }

    public ResponseEntity<Object> updateProjectById(Projects project) {
        Projects projects = projectRepository.findById(project.getId()).orElse(null);

        if (projects == null) return ResponseEntity.badRequest().build();


        if (project.getName() != null) projects.setName(project.getName());
        if (project.getDescription() != null) projects.setDescription(project.getDescription());
        if (project.getTagColor() != null) projects.setTagColor(project.getTagColor());
        if (project.getTag() != null) projects.setTag(project.getTag());
        if (project.getStatus() != null) {
            projects.setStatus(project.getStatus());
            if (Objects.equals(project.getStatus(), "3")) projects.setEndDate(new Date());
        }


        projectRepository.save(projects);

        return ResponseEntity.ok(projects);

    }

    public ResponseEntity<Object> deleteProject(Long projectId) {
        Projects project = projectRepository.findById(projectId).orElse(null);

        if (project == null) return ResponseEntity.badRequest().build();

        project.setMembers(List.of());
        project.setTasks(List.of());

        projectRepository.deleteById(projectId);

        return ResponseEntity.ok().build();
    }
}
