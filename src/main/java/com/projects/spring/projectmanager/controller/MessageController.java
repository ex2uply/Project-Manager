package com.projects.spring.projectmanager.controller;


import com.projects.spring.projectmanager.Model.Chat;
import com.projects.spring.projectmanager.Model.Message;
import com.projects.spring.projectmanager.Model.User;
import com.projects.spring.projectmanager.request.CreateMessageRequest;
import com.projects.spring.projectmanager.response.MessageResponse;
import com.projects.spring.projectmanager.service.MessageService;
import com.projects.spring.projectmanager.service.ProjectService;
import com.projects.spring.projectmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    private final UserService userService;

    private final ProjectService projectService;


    public MessageController(MessageService messageService, UserService userService, ProjectService projectService) {
        this.messageService = messageService;
        this.userService = userService;
        this.projectService = projectService;
    }


    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request) throws Exception {
//        User user = userService.findUserById(request.getSenderId());

        Chat chats = projectService.getChatByProjectId(request.getProjectId());

        if(chats == null){
            throw new Exception("Chat not found");
        }
        Message sentMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent());

        return ResponseEntity.ok(sentMessage);

    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByProjectId(@PathVariable Long projectId) throws Exception {
        List<Message> messages = messageService.getMessageByProjectId(projectId);

        return ResponseEntity.ok(messages);
    }

}
