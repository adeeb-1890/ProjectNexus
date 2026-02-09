package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.Chat;
import com.Adeeb.ProjectManagementSystem.Model.Messages;
import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Repository.MessageRepo;
import com.Adeeb.ProjectManagementSystem.Repository.ProjectRepo;
import com.Adeeb.ProjectManagementSystem.Repository.UserRepo;
import com.Adeeb.ProjectManagementSystem.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImplementation implements MessageService{

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProjectService projectService;

    @Override
    public Messages sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepo.findById(senderId)
                .orElseThrow(() -> new Exception("User not found"));

        Chat chat = projectService.getProjectById(projectId).getChat();

        Messages message = new Messages();
        message.setContent(content);
        message.setSender(sender);
        message.setCreationTime(LocalDateTime.now());
        message.setChat(chat);

        Messages savedMessage = messageRepo.save(message);
        chat.getMessages().add(savedMessage);
        return savedMessage;

    }

    @Override
    public List<Messages> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);

        List<Messages> messagesList = messageRepo.findByChatIdOrderByCreatedArtAsc(chat.getId());

        return messagesList;
    }
}
