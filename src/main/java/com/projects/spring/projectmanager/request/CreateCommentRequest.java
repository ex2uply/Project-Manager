package com.projects.spring.projectmanager.request;


import lombok.Data;

@Data
public class CreateCommentRequest {

    private Long issueID;
    private Long userId;
    private String content;
}
