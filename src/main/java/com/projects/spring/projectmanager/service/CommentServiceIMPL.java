package com.projects.spring.projectmanager.service;

import com.projects.spring.projectmanager.Model.Comment;
import com.projects.spring.projectmanager.Model.Issues;
import com.projects.spring.projectmanager.Model.User;
import com.projects.spring.projectmanager.repository.CommentRepo;
import com.projects.spring.projectmanager.repository.IssueRepo;
import com.projects.spring.projectmanager.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CommentServiceIMPL implements CommentService{
    private final CommentRepo commentRepo;

    private final IssueRepo issueRepo;
    private final UserRepo userRepo;

    public CommentServiceIMPL(CommentRepo commentRepo, IssueRepo issueRepo, UserRepo userRepo) {
        this.commentRepo = commentRepo;
        this.issueRepo = issueRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Comment createComment(Long issueId, Long userId,String content) throws Exception {
        Optional<Issues> issuesOptional = issueRepo.findById(issueId);
        Optional<User> userOptional = userRepo.findById(userId);
        if(issuesOptional.isEmpty()){
            throw new Exception("Issue not found" + issueId);
        }
        if(userOptional.isEmpty()){
            throw new Exception("User not found" + userId);
        }
        Issues issues = issuesOptional.get();
        User user = userOptional.get();


        Comment comment = new Comment();
        comment.setIssues(issues);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreatedAt(java.time.LocalDate.now());

        Comment comment1 = commentRepo.save(comment);

        issues.getComments().add(comment1);

        issueRepo.save(issues);

        return comment1;


    }

    @Override
    public void deleteComment(Long commentId, Long Userid) throws Exception {
        Optional<Comment> commentOptional = commentRepo.findById(commentId);
        Optional<User> userOptional = userRepo.findById(Userid);
        if(commentOptional.isEmpty()){
            throw new Exception("Comment not found" + commentId);
        }
        if(userOptional.isEmpty()){
            throw new Exception("User not found" + Userid);
        }
        Comment comment = commentOptional.get();
        User user = userOptional.get();
        if(comment.getUser().getId() != user.getId()){
            throw new Exception("User not authorized to delete this comment");
        }

        commentRepo.delete(comment);

    }

    @Override
    public List<Comment> findCommentsByIssueId(Long issueId) throws Exception {
        return commentRepo.findByIssuesId(issueId);
    }
}
