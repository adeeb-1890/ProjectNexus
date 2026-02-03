package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.Invitation;

public interface InvitationService {

    public void sendInvitation(String email , Long projectId);

    public Invitation acceptInvitation(String token , Long userId) throws Exception;

    public String getTokenByUserMail(String userMail);

    public void deleteToken(String token);
}
