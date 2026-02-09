package com.Adeeb.ProjectManagementSystem.Controller;


import com.Adeeb.ProjectManagementSystem.Model.Chat;
import com.Adeeb.ProjectManagementSystem.Model.Messages;
import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Request.CreateMessageRequest;
import com.Adeeb.ProjectManagementSystem.Service.MessageService;
import com.Adeeb.ProjectManagementSystem.Service.ProjectService;
import com.Adeeb.ProjectManagementSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

st;

@RestController
@RequestMapping("api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @PostMapping("/send")
    public ResponseEntity<Messages> sendMessage(@RequestBody CreateMessageRequest messageRequest) throws Exception{

        User user = userService.findUserById(messageRequest.getSenderId());

        Chat chat = projectService.getChatByProjectId(messageRequest.getProjectId()).getProject().getChat();

        if(chat == null) throw new Exception("Chat not found");

        Messages sentMessage = messageService.sendMessage(messageRequest.getSenderId() , messageRequest.getProjectId() , messageRequest.getContent());

        return ResponseEntity.ok(sentMessage);

    }

    @GetMapping("chat/projectId")
    public ResponseEntity<List<Messages>> getMessagesByChatId(@PathVariable Long projectId) throws Exception{

        List<Messages> messagesList = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messagesList);
    }
}
