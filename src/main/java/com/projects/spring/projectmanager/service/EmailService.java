package com.projects.spring.projectmanager.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String userEmail, String link) throws MessagingException;
}
