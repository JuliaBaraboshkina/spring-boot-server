package com.parazitik.graduatework.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "project_id")
    private Long projectId;

    // 1 - низкий, 2 - средний, 3 - высокий
    @Column(name = "priority")
    private int priority;

    @Column(name = "deadline")
    private Date deadline;

    @Column(name = "colour")
    private String colour;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToMany
    private List<Users> assignee;

    @OneToMany
    private List<Subtasks> subtasks;

    @ManyToMany
    private List<Comments> comments;

    @OneToMany
    private List<Files> files;
}
