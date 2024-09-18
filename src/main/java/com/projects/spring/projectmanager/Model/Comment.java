package com.projects.spring.projectmanager.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    private long id;

    @ManyToOne
    private Issues issues;

    private String content;

    private LocalDate createdAt;

    @ManyToOne
    private User user;
}
