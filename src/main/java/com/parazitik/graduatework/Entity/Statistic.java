package com.parazitik.graduatework.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Table(name = "statistic")
@Data
public class Statistic {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private Long userId;

    private Date completeSubtask;
    private Date completeTask;
    private Date createProject;
    private Date createTask;
}
