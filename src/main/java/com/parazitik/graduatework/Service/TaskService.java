package com.parazitik.graduatework.Service;

import com.parazitik.graduatework.Entity.*;
import com.parazitik.graduatework.Model.TaskCalendarDTO;
import com.parazitik.graduatework.Model.TaskCreateDTO;
import com.parazitik.graduatework.Model.TaskDTO;
import com.parazitik.graduatework.Repository.ProjectRepository;
import com.parazitik.graduatework.Repository.SubtaskRepository;
import com.parazitik.graduatework.Repository.TaskRepository;
import com.parazitik.graduatework.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubtaskService subtaskService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private StatisticService statisticService;

    public ResponseEntity<Object> createTask(TaskCreateDTO tasks) {

        System.out.println(tasks);
        Tasks task = new Tasks();

        if (tasks.getProjectId() == null) {
            return ResponseEntity.badRequest().body("Не выбран проект!");
        }

        Projects projects = projectRepository.findById(tasks.getProjectId()).orElse(null);
        if (projects == null) {
            return ResponseEntity.badRequest().body("Не найден проект!");
        }

        if (tasks.getAssignee() != null) {
            for (Long assigneeId : tasks.getAssignee()) {
                Users user = userRepository.findById(assigneeId).orElse(null);
                if (user == null) {
                    return ResponseEntity.badRequest().body("Не найден один из назначенных пользователей!");
                }else {
                    if (task.getAssignee() != null) {
                        task.getAssignee().add(user);
                    } else {
                        task.setAssignee(new ArrayList<>(Collections.singletonList(user)));
                    }
                }


            }
        }

        task.setName(tasks.getName());
        task.setOwnerId(tasks.getOwnerId());
        task.setName(tasks.getName());
        task.setDescription(tasks.getDescription());
        task.setStartDate(tasks.getStartDate());
        task.setEndDate(tasks.getEndDate());
        task.setPriority(tasks.getPriority());
        task.setProjectId(tasks.getProjectId());
        task.setCreatedAt(new Date());
        task.setDeadline(task.getEndDate());
        task.setStatus("1");

        task.setColour(projects.getTagColor());

        taskRepository.save(task);

        if (projects.getTasks() != null) {
            projects.getTasks().add(task);
        } else {
            projects.setTasks(new ArrayList<>(Collections.singletonList(task)));
        }

        projectRepository.save(projects);

        statisticService.createTask(tasks.getOwnerId());

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> getAllAssigneeTasks(Long userId) {

        List<Tasks> task = taskRepository.findAllTasksByAssigneeId(userId);
        List<TaskDTO> resultTask = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        task.forEach(tasks -> {

            Projects project = projectRepository.findById(tasks.getProjectId()).orElse(null);

            System.out.println(tasks);

            assert project != null;
            resultTask.add(TaskDTO.builder()
                    .id(tasks.getId())
                    .color(project.getTagColor())
                    .deadline(df.format(tasks.getDeadline()))
                    .title(tasks.getName())
                    .subtasks(taskRepository.countCompletedSubtasksByTaskId(tasks.getId()))
                    .totalSubtasks(taskRepository.countTotalSubtasksByTaskId(tasks.getId()))
                    .assignees(tasks.getAssignee())
                    .priority(tasks.getPriority())
                    .build());
        });

        return ResponseEntity.ok(resultTask);
    }

    public ResponseEntity<Object> getAllAssigneeTasksByProjectId(Long projectId, String status) {

        List<Tasks> task = status.equals("0") ?
                taskRepository.findTasksByProjectId(projectId) :
                taskRepository.findTasksByProjectIdAndStatus(projectId, status);
        List<TaskDTO> resultTask = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        task.forEach(tasks -> {

            Projects project = projectRepository.findById(tasks.getProjectId()).orElse(null);

            assert project != null;
            resultTask.add(TaskDTO.builder()
                    .id(tasks.getId())
                    .color(project.getTagColor())
                    .deadline(tasks.getDeadline() == null ? df.format(new Date()) : df.format(tasks.getDeadline()))
                    .title(tasks.getName())
                    .subtasks(taskRepository.countCompletedSubtasksByTaskId(tasks.getId()))
                    .totalSubtasks(taskRepository.countTotalSubtasksByTaskId(tasks.getId()))
                    .build());
        });

        return ResponseEntity.ok(resultTask);
    }

    public ResponseEntity<Object> getAllAssigneeForCalendarTasks(Long userId, String status) {

        List<Tasks> task = status.equals("0") ? taskRepository.findAllTasksByAssigneeId(userId) : taskRepository.findAllTasksByAssigneeIdAndStatus(userId, status);
        List<TaskCalendarDTO> resultTask = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        DateFormat tf = new SimpleDateFormat("HH:00");

        task.forEach(tasks -> {

            Projects project = projectRepository.findById(tasks.getProjectId()).orElse(null);

            System.out.println(tf.format(tasks.getDeadline()));

            assert project != null;
            resultTask.add(TaskCalendarDTO.builder()
                    .id(tasks.getId())
                    .color(project.getTagColor())
                    .date(df.format(tasks.getDeadline()))
                    .title(tasks.getName())
                    .time(tf.format(tasks.getDeadline()))
                    .category(project.getName())
                    .build());
        });

        return ResponseEntity.ok(resultTask);
    }

    public ResponseEntity<Object> addAssigneeToTask(Long userId, Long taskId) {
        Tasks task = taskRepository.findTasksById(taskId).orElse(null);
        Users user = userRepository.findById(userId).orElse(null);

        if (task == null) return ResponseEntity.badRequest().body("Не найдена задача!");
        if (user == null) return ResponseEntity.badRequest().body("Не найден пользователь!");

        if (task.getAssignee() != null) {
            task.getAssignee().add(user);
        } else {
            task.setAssignee(new ArrayList<>(Collections.singletonList(user)));
        }

        taskRepository.save(task);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> getCurrentTask(Long taskId) {
        Tasks tasks = taskRepository.findById(taskId).orElse(null);

        if (tasks == null) return ResponseEntity.badRequest().body("Нет такой задачи");

        return ResponseEntity.ok(taskRepository.findById(taskId));
    }

    public ResponseEntity<Object> createSubtask(Long taskId, Subtasks subtasks) {
        Subtasks subtask = subtaskService.createSubtask(subtasks);

        Tasks task = taskRepository.findById(taskId).orElse(null);

        if (task == null) return ResponseEntity.badRequest().body("Нет такой задачи");

        if (task.getSubtasks() != null) {
            task.getSubtasks().add(subtask);
        } else {
            task.setSubtasks(new ArrayList<>(Collections.singletonList(subtask)));
        }

        taskRepository.save(task);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> toggleSubtask(Long userId, Long subtaskId) {
        boolean status = subtaskService.toggleSubtask(subtaskId);

        if (status) statisticService.toggleSubtask(userId);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> addComments(Long taskId, Comments comments) {
        Comments comment = commentService.addComment(taskId, comments);

        comment.setCommentDate(new Date());

        Tasks task = taskRepository.findById(taskId).orElse(null);

        if (task == null) return ResponseEntity.badRequest().body("Нет такой задачи");

        if (task.getComments() != null) {
            task.getComments().add(comment);
        } else {
            task.setComments(new ArrayList<>(Collections.singletonList(comment)));
        }

        taskRepository.save(task);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> addMemberToTask(Long taskId, Long memberId) {
        Tasks task = taskRepository.findTasksById(taskId).orElse(null);

        if (task == null) return ResponseEntity.badRequest().build();

        Users user = userRepository.findById(memberId).orElse(null);

        if (user == null) return ResponseEntity.badRequest().build();

        if (task.getAssignee() != null) {
            for (Users assignee: task.getAssignee()) {
                if (Objects.equals(assignee.getId(), memberId)) return ResponseEntity.badRequest().build();
            }
            task.getAssignee().add(user);
        } else {
            task.setAssignee(new ArrayList<>(Collections.singletonList(user)));
        }

        taskRepository.save(task);

        return ResponseEntity.ok().build();
    }
}
