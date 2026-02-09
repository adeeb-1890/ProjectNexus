package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.Issue;
import com.Adeeb.ProjectManagementSystem.Model.Project;
import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Repository.IssueRepo;
import com.Adeeb.ProjectManagementSystem.Request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImplementation implements IssueService{

    @Autowired
    private IssueRepo issueRepo;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public Issue getIssueById(Long issueId) throws Exception {
        Optional<Issue> issue = issueRepo.findById(issueId);

        if(issue.isPresent()){
            return issue.get();
        }

        throw new Exception("No issue found");
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepo.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issueRequest.getProjectFk());

        Issue newissue = new Issue();
        newissue.setTitle(issueRequest.getTitle());
        newissue.setDescription(issueRequest.getDescription());
        newissue.setStatus(issueRequest.getStatus());
        newissue.setProjectFk(issueRequest.getProjectFk());
        newissue.setDueDate(issueRequest.getDueDate());
        newissue.setPriority(issueRequest.getPriority());
        newissue.setProject(project);

        return issueRepo.save(newissue);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
        getIssueById(issueId);
        issueRepo.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(issueId);

        issue.setAssignee(user);

        issueRepo.save(issue);
        return issue;
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);

        return issueRepo.save(issue);
    }
}
