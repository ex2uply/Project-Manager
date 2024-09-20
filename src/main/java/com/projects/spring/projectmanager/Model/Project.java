package com.projects.spring.projectmanager.Model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean status;


    private List<String> tags;


    @JsonIgnore
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Chat chat;

    @ManyToOne
    private User owner;


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)

    private List<Issues> issues = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> team = new ArrayList<>();




}
