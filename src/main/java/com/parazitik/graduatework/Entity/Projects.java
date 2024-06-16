package com.parazitik.graduatework.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.List;

import static org.hibernate.annotations.CascadeType.ALL;

@Entity
@Table(name = "projects")
@Data
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "name")
    private String name;

    @Column(name = "tag")
    private String tag;

    @Column(name = "tag_color")
    private String tagColor;

    @Column(name = "description")
    private String description;

    //1 - низкий, 2 - средний, 3 - высокий
    @Column(name = "priority")
    private int priority = 1;

    @JsonFormat(pattern = "dd.MM.yyyy HH")
    @Column(name = "deadline")
    private Date deadline;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "start_date")
    @JsonFormat(pattern = "dd.MM.yyyy HH")
    private Date startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "dd.MM.yyyy HH")
    private Date endDate;

    @ManyToMany
    @Cascade(ALL)
    @JoinTable(
            name = "projects_members",
            joinColumns = @JoinColumn(name = "projects_id"),
            inverseJoinColumns = @JoinColumn(name = "members_id")
    )
    @Column(name = "members")
    private List<Users> members;

    @OneToMany
    @Cascade(ALL)
    @Column(name = "tasks")
    private List<Tasks> tasks;
}
