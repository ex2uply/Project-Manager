package com.projects.spring.projectmanager.service;

import com.projects.spring.projectmanager.Model.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long issueID, Long userId, String content) throws Exception;

    void deleteComment(Long CommentId ,  Long id) throws Exception;

    List<Comment> findCommentsByIssueId(Long issueId) throws Exception;
}
