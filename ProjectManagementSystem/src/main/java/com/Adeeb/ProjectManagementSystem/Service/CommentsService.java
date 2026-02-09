package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.Comments;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;

public interface CommentsService {
    Comments createCommment(Long issueId , Long userid , String comment) throws Exception;

    void deleteComment(Long commentId , Long userId) throws  Exception;

    List<Comments> findCommentsByIssue(Long issueId) throws Exception;
}
