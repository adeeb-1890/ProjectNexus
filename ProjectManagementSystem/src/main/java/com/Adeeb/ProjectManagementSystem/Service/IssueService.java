package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.Issue;
import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue getIssueById(Long issueId) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issue , User user)throws Exception;

    void deleteIssue(Long issueId , Long userId)throws Exception;

    Issue addUserToIssue(Long issueId , long userId) throws Exception;

    Issue updateStatus(Long issueId , String status) throws Exception;

}
