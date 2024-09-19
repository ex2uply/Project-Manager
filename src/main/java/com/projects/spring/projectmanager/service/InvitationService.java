package com.projects.spring.projectmanager.service;

import com.projects.spring.projectmanager.Model.Invitation;

public interface InvitationService {

    void sendInvitation(String email, Long projectId) throws Exception;

    Invitation acceptInvitation(String token,Long userId) throws Exception;

    String getTokenByUserMail(String userEmail) throws Exception;

    void deleteToken(String token)  throws Exception;
}
