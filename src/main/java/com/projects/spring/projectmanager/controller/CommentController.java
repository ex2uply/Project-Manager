package com.projects.spring.projectmanager.controller;

import com.projects.spring.projectmanager.Model.Comment;
import com.projects.spring.projectmanager.Model.User;
import com.projects.spring.projectmanager.request.CreateCommentRequest;
import com.projects.spring.projectmanager.response.MessageResponse;
import com.projects.spring.projectmanager.service.CommentService;
import com.projects.spring.projectmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }


    @PostMapping()
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest request,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Comment comment = commentService.createComment(request.getIssueID(), user.getId(), request.getContent());

        return new ResponseEntity<>(comment , HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Comment deleted successfully");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentsByIssueId(
            @PathVariable Long issueId) throws Exception {

        List<Comment> comments = commentService.findCommentsByIssueId(issueId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
