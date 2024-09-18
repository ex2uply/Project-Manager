package com.projects.spring.projectmanager.service;


import com.projects.spring.projectmanager.Model.Chat;
import org.springframework.stereotype.Service;


public interface ChatService {

    Chat createChat(Chat chat) throws Exception;
}
