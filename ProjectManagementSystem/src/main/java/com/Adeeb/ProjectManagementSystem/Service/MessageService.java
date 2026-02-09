package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.Messages;

import java.util.List;

public interface MessageService {

    Messages sendMessage(Long senderId , Long projectId , String content) throws Exception;

    List<Messages> getMessagesByProjectId(Long projectId)throws Exception;
}
