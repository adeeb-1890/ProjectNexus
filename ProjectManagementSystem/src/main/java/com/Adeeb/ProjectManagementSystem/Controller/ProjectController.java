package com.Adeeb.ProjectManagementSystem.Controller;

import com.Adeeb.ProjectManagementSystem.Model.*;
import com.Adeeb.ProjectManagementSystem.Request.InvitationRequest;
import com.Adeeb.ProjectManagementSystem.Response.MessageResponse;
import com.Adeeb.ProjectManagementSystem.Service.InvitationService;
import com.Adeeb.ProjectManagementSystem.Service.ProjectService;
import com.Adeeb.ProjectManagementSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping
     public ResponseEntity<List<Project>> getProjects(@RequestParam(required = false)String category,
                                                      @RequestParam(required = false)String tag ,
                                                      @RequestHeader("Authorization")String jwtToken) throws Exception {

         User user = userService.findUserProfileByJwt(jwtToken);
         List<Project> projects = projectService.getProjectByTeam(user , category , tag);

         return new ResponseEntity<>(projects , HttpStatus.OK);
     }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(Long projectId,
                                                     @RequestHeader("Authorization")String jwtToken) throws Exception {

        User user = userService.findUserProfileByJwt(jwtToken);
        Project project = projectService.getProjectById(projectId);

        return new ResponseEntity<>(project , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestHeader("Authorization")String jwtToken ,
                                                 @RequestBody Project project) throws Exception {

        User user = userService.findUserProfileByJwt(jwtToken);
        Project createdProject = projectService.createProject(project , user);

        return new ResponseEntity<>(createdProject , HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId,
                                                 @RequestHeader("Authorization")String jwtToken ,
                                                 @RequestBody Project project) throws Exception {

        User user = userService.findUserProfileByJwt(jwtToken);
        Project updatedProject = projectService.updateProject(project , projectId);

        return new ResponseEntity<>(updatedProject , HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(@PathVariable Long projectId,
                                                 @RequestHeader("Authorization")String jwtToken
                                                ) throws Exception {

        User user = userService.findUserProfileByJwt(jwtToken);
        projectService.deleteProject(projectId , user.getId());
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessageResponse("Project deleted successfully");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Project>> createProject(@RequestHeader("Authorization")String jwtToken ,
                                                 @RequestParam(required = false)String keyword) throws Exception {

        User user = userService.findUserProfileByJwt(jwtToken);
        List<Project> projects = projectService.searchProjects(keyword , user);

        return new ResponseEntity<>(projects , HttpStatus.OK);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(Long projectId,
                                                  @RequestHeader("Authorization")String jwtToken) throws Exception {

        User user = userService.findUserProfileByJwt(jwtToken);
        Chat chat = projectService.getChatByProjectId(projectId);

        return new ResponseEntity<>(chat , HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> InviteToProject(
            @RequestBody InvitationRequest invitationRequest,
            @RequestHeader("Authorization")String jwtToken ,
            @RequestBody Project project) throws Exception {

        User user = userService.findUserProfileByJwt(jwtToken);
        invitationService.sendInvitation(invitationRequest.getEmail() , invitationRequest.getProjectId());
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessageResponse("Invitation link sent.");
        return new ResponseEntity<>(messageResponse , HttpStatus.OK);
    }


    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInvitationOfProject(
            @RequestParam String token,
            @RequestHeader("Authorization")String jwtToken ,
            @RequestBody Project project) throws Exception {

        User user = userService.findUserProfileByJwt(jwtToken);
        Invitation invitation =  invitationService.acceptInvitation(token , user.getId());
        projectService.addUserToProject(invitation.getProjectId() , user.getId());

        return new ResponseEntity<>(invitation , HttpStatus.ACCEPTED);
    }



}
