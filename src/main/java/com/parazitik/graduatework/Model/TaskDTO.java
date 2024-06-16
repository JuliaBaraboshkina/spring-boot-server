package com.parazitik.graduatework.Model;

import com.parazitik.graduatework.Entity.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TaskDTO {
    private Long id;
    private String title;
    private String deadline;
    private Long subtasks;
    private Long totalSubtasks;
    private String color;
    private List<Users> assignees;
    private Integer priority;
}

