package com.projects.spring.projectmanager.service;

import com.projects.spring.projectmanager.Model.Chat;
import com.projects.spring.projectmanager.repository.ChatRepo;
import org.springframework.stereotype.Service;


@Service
public class ChatServiceIMPL implements ChatService{

    private final ChatRepo repo;

    public ChatServiceIMPL(ChatRepo repo) {
        this.repo = repo;
    }

    @Override
    public Chat createChat(Chat chat) {

        return repo.save(chat);
    }
}
