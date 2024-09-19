package com.projects.spring.projectmanager.service;


import com.projects.spring.projectmanager.Model.Chat;
import com.projects.spring.projectmanager.Model.Message;
import com.projects.spring.projectmanager.Model.User;
import com.projects.spring.projectmanager.repository.MessageRepo;
import com.projects.spring.projectmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceIMPL implements MessageService{

    private final MessageRepo messageRepo;
    private final UserRepo userRepo;
    private final ProjectService projectService;

    public MessageServiceIMPL(MessageRepo messageRepo, UserRepo userRepo, ProjectService projectService) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
        this.projectService = projectService;
    }

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepo.findById(senderId).orElseThrow(() -> new Exception("User not found" + senderId));

        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setContent(content);
        message.setUser(sender);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);
        Message message1 = messageRepo.save(message);

        chat.getMessages().add(message1);

        return message1;
    }

    @Override
    public List<Message> getMessageByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);

        return messageRepo.findByChatIdOrderByCreatedAtAsc(chat.getId());

    }
}
