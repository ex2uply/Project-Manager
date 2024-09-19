package com.projects.spring.projectmanager.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IssuesRequest {
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private Long projectId;
}
