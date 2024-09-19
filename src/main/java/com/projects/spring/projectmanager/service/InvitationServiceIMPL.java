package com.projects.spring.projectmanager.service;


import com.projects.spring.projectmanager.Model.Invitation;
import com.projects.spring.projectmanager.repository.InvitationRepo;
import com.projects.spring.projectmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceIMPL implements InvitationService{

    private final InvitationRepo invitationRepo;
    private final EmailService emailService;

    public InvitationServiceIMPL(InvitationRepo invitationRepo, UserRepo userRepo, EmailService emailService) {
        this.invitationRepo = invitationRepo;
        this.emailService = emailService;
    }

    @Override
    public void sendInvitation(String email, Long projectId) throws Exception {

        //creating random token
        String invitationToken = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);

        invitationRepo.save(invitation);

        String invitationLink = "http://localhost:8081/accept_invitation?token="+invitationToken;
        emailService.sendEmail(email,invitationLink);
    }
    //returning the invitation
    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {
        Invitation invitation = invitationRepo.findByToken(token);
        if(invitation==null){
            throw new Exception("Invalid Token - Invitation not found");
        }
        return invitation;
    }

    //returning the token
    @Override
    public String getTokenByUserMail(String userEmail) throws Exception {
        Invitation invitation = invitationRepo.findByEmail(userEmail);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) throws Exception {
        Invitation invitation = invitationRepo.findByToken(token);
        if(invitation==null){
            throw new Exception("Invalid Token - Invitation not found");
        }
        invitationRepo.delete(invitation);

    }
}
