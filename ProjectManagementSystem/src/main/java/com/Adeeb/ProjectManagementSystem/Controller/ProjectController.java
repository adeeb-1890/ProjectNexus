package com.Adeeb.ProjectManagementSystem.Controller;

import com.Adeeb.ProjectManagementSystem.Model.Chat;
import com.Adeeb.ProjectManagementSystem.Model.Project;
import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Response.MessageResponse;
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

    @GetMapping
     public ResponseEntity<List<Project>> getProjects(@RequestParam(required = false)String category,
                                                      @RequestParam(required = false)String tag ,
                                                      @RequestHeader("Authorization")String token) throws Exception {

         User user = userService.findUserProfileByJwt(token);
         List<Project> projects = projectService.getProjectByTeam(user , category , tag);

         return new ResponseEntity<>(projects , HttpStatus.OK);
     }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(Long projectId,
                                                     @RequestHeader("Authorization")String token) throws Exception {

        User user = userService.findUserProfileByJwt(token);
        Project project = projectService.getProjectById(projectId);

        return new ResponseEntity<>(project , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestHeader("Authorization")String token ,
                                                 @RequestBody Project project) throws Exception {

        User user = userService.findUserProfileByJwt(token);
        Project createdProject = projectService.createProject(project , user);

        return new ResponseEntity<>(createdProject , HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId,
                                                 @RequestHeader("Authorization")String token ,
                                                 @RequestBody Project project) throws Exception {

        User user = userService.findUserProfileByJwt(token);
        Project updatedProject = projectService.updateProject(project , projectId);

        return new ResponseEntity<>(updatedProject , HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(@PathVariable Long projectId,
                                                 @RequestHeader("Authorization")String token
                                                ) throws Exception {

        User user = userService.findUserProfileByJwt(token);
        projectService.deleteProject(projectId , user.getId());
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessageResponse("Project deleted successfully");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Project>> createProject(@RequestHeader("Authorization")String token ,
                                                 @RequestParam(required = false)String keyword) throws Exception {

        User user = userService.findUserProfileByJwt(token);
        List<Project> projects = projectService.searchProjects(keyword , user);

        return new ResponseEntity<>(projects , HttpStatus.OK);
    }

    @GetMapping("/{projectId/chat}")
    public ResponseEntity<Chat> getChatByProjectId(Long projectId,
                                                  @RequestHeader("Authorization")String token) throws Exception {

        User user = userService.findUserProfileByJwt(token);
        Chat chat = projectService.getChatByProjectId(projectId);

        return new ResponseEntity<>(chat , HttpStatus.OK);
    }

}
