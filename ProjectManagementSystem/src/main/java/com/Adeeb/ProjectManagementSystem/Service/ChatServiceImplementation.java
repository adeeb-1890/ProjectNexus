package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.Chat;
import com.Adeeb.ProjectManagementSystem.Repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImplementation implements ChatService{
    @Autowired
    private ChatRepo chatRepo;
    @Override
    public Chat createChat(Chat chat) {
        return chatRepo.save(chat);
    }
}
