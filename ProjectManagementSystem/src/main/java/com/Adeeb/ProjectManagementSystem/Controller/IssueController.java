package com.Adeeb.ProjectManagementSystem.Controller;


import com.Adeeb.ProjectManagementSystem.DTO.IssueDTO;
import com.Adeeb.ProjectManagementSystem.Model.Issue;
import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Repository.IssueRepo;
import com.Adeeb.ProjectManagementSystem.Request.IssueRequest;
import com.Adeeb.ProjectManagementSystem.Response.MessageResponse;
import com.Adeeb.ProjectManagementSystem.Service.IssueService;
import com.Adeeb.ProjectManagementSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolvedModule;
import java.util.List;

@RestController
@RequestMapping("api/issue")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception{
        return new ResponseEntity<>(issueService.getIssueById(issueId) , HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception{
        return new ResponseEntity<>(issueService.getIssueByProjectId(projectId) , HttpStatus.OK);
    }

    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issueRequest,
                                                @RequestHeader("Authorization") String jwtToken)throws Exception{
        User tokenUser = userService.findUserProfileByJwt(jwtToken);
        User user = userService.findUserById(tokenUser.getId());

        Issue createdIssue = issueService.createIssue(issueRequest , tokenUser);
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setDescription(createdIssue.getDescription());
        issueDTO.setDueDate(createdIssue.getDueDate());
        issueDTO.setId(createdIssue.getId());
        issueDTO.setPriority(createdIssue.getPriority());
        issueDTO.setProject(createdIssue.getProject());
        issueDTO.setProjectFk(createdIssue.getProjectFk());
        issueDTO.setStatus(createdIssue.getStatus());
        issueDTO.setTitle(createdIssue.getTitle());
        issueDTO.setTags(createdIssue.getTags());
        issueDTO.setAssignee(createdIssue.getAssignee());

        return new ResponseEntity<>(issueDTO , HttpStatus.OK);
    }

    @DeleteMapping("/issueId")
    public ResponseEntity<MessageResponse> deleteIssueById(@PathVariable Long issueId ,
                                                           @RequestHeader("Authorization")String jwtToken)
        throws Exception{
        User user = userService.findUserProfileByJwt(jwtToken);
        issueService.deleteIssue(issueId , user.getId());

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessageResponse("Issue deleted successfully.");
        return ResponseEntity.ok(messageResponse);
    }


    @PutMapping("{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId ,
                                                @PathVariable Long userId) throws Exception{

        Issue issue = issueService.addUserToIssue(issueId , userId);
        return ResponseEntity.ok(issue);
    }

    public ResponseEntity<Issue> updateIssueStatus(@PathVariable String status ,
                                                   @PathVariable Long issueId) throws Exception{
        Issue issue = issueService.updateStatus(issueId , status);
        return new ResponseEntity<>(issue , HttpStatus.OK);
    }


}
