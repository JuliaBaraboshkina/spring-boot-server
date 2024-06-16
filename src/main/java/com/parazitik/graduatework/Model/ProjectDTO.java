package com.parazitik.graduatework.Model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ProjectDTO {
    private Long ownerId;
    private String name;
    private String tag;
    private String tagColor;
    private String description;
    private Integer priority;
    private String deadline;
    private String status;
    private String startDate;
    private String endDate;
    private List<MemberDTO> members;
}

