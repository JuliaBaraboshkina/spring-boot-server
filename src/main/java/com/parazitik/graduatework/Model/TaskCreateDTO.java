package com.parazitik.graduatework.Model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
public class TaskCreateDTO {
    private Long id;
    private String name;
    private Long ownerId;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer priority;
    private Long projectId;
    private List<Long> assignee;
}