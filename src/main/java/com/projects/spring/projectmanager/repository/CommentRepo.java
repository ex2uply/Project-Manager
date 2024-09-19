package com.projects.spring.projectmanager.repository;

import com.projects.spring.projectmanager.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByIssuesId(Long issueId);
}
