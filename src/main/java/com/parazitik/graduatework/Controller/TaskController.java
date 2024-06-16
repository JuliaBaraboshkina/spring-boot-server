package com.parazitik.graduatework.Controller;

import com.parazitik.graduatework.Entity.Comments;
import com.parazitik.graduatework.Entity.Subtasks;
import com.parazitik.graduatework.Model.TaskCreateDTO;
import com.parazitik.graduatework.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("create")
    public ResponseEntity<Object> createTask(@RequestBody TaskCreateDTO tasks) {
        return taskService.createTask(tasks);
    }

    @PutMapping("add-assignee")
    public ResponseEntity<Object> addAssignee(@RequestParam Long userId, @RequestParam Long taskId) {
        return taskService.addAssigneeToTask(userId, taskId);
    }

    @GetMapping("get-all-tasks")
    public ResponseEntity<Object> getAllTasksByUserId(@RequestParam Long userId) {
        return taskService.getAllAssigneeTasks(userId);
    }

    @GetMapping("get-all-tasks-project-id")
    public ResponseEntity<Object> getAllTasksByProjectId(@RequestParam Long projectId, @RequestParam String status) {
        return taskService.getAllAssigneeTasksByProjectId(projectId, status);
    }

    @GetMapping("get-all-tasks-calendar")
    public ResponseEntity<Object> getAllTasksForCalendarByUserId(@RequestParam Long userId, @RequestParam String status) {
        return taskService.getAllAssigneeForCalendarTasks(userId, status);
    }

    @GetMapping("get")
    public ResponseEntity<Object> getCurrentTask(@RequestParam Long taskId) {
        return taskService.getCurrentTask(taskId);
    }

    @PutMapping("add-subtask")
    public ResponseEntity<Object> addSubtask(@RequestParam Long taskId, @RequestBody Subtasks subtask){
        System.out.println(taskId);
        System.out.println(subtask);

        return taskService.createSubtask(taskId, subtask);
    }

    @PutMapping("toggle-subtask")
    public ResponseEntity<Object> toggleSubtaskStatus(@RequestParam Long userId, @RequestParam Long subtaskId) {
        return taskService.toggleSubtask(userId, subtaskId);
    }

    @PostMapping("add-comment")
    public ResponseEntity<Object> addComment(@RequestParam Long taskId, @RequestBody Comments comments) {
        return taskService.addComments(taskId, comments);
    }

    @PutMapping("add-member")
    public ResponseEntity<Object> addMember(@RequestParam Long taskId, @RequestParam Long userId) {
        return taskService.addMemberToTask(taskId, userId);
    }
}
