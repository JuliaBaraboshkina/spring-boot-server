package com.parazitik.graduatework.Model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class StatisticDTO {
    private Long taskCompleted;
    private Long taskCreated;
    private Long taskCreatedAllTime;
    private Long projectCreatedAllTime;

    private List<Long> taskCompletedByDays;

    private Integer soloProjects;
    private Integer teamProjects;
}
