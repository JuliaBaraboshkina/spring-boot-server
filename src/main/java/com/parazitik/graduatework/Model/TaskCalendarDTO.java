package com.parazitik.graduatework.Model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskCalendarDTO {
    private Long id;
    private String title;
    private String date;
    private String category;
    private String time;
    private String color;
}
