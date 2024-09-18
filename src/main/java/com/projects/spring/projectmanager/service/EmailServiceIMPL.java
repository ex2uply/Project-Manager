package com.projects.spring.projectmanager.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceIMPL implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceIMPL(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String userEmail, String link) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String subject = "Project Manager: Join Project Team Invitation";
        String message = "<h1>Click the link below to join the project</h1><br><a href='"+link+"'>Join Project</a>";


            helper.setText(message, true);
            helper.setTo(userEmail);
            helper.setSubject(subject);

        try{
            javaMailSender.send(mimeMessage);
        }
        catch (MailSendException e){
            throw new MailSendException("Failed to send email");
        }
    }
}