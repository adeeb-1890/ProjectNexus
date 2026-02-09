package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.Comments;
import com.Adeeb.ProjectManagementSystem.Model.Issue;
import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Repository.CommentRepo;
import com.Adeeb.ProjectManagementSystem.Repository.IssueRepo;
import com.Adeeb.ProjectManagementSystem.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CommentsServiceImplementation implements CommentsService{

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private IssueRepo issueRepo;

    @Autowired
    private UserRepo userRepo;


    @Override
    public Comments createCommment(Long issueId, Long userid, String comment)throws Exception{
        Optional<Issue> optionalIssue = issueRepo.findById(issueId);
        Optional<User> optionalUser = userRepo.findById(userid);

        if(optionalIssue.isEmpty()){
            throw new Exception("Issue not found");
        }

        if(optionalUser.isEmpty())
            throw new Exception("User not found");

        Issue issue = optionalIssue.get();
        User user = optionalUser.get();

        Comments comments = new Comments();

        comments.setIssue(issue);
        comments.setUser(user);
        comments.setCreatedTime(LocalDateTime.now());
        comments.setContent(comment);

        Comments savedComment = commentRepo.save(comments);

        issue.getComments().add(savedComment);
        return savedComment;

    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception{
        Optional<Comments> optionalComment = commentRepo.findById(commentId);
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalComment.isEmpty()){
            throw new Exception("Issue not found");
        }

        if(optionalUser.isEmpty())
            throw new Exception("User not found");

        Comments comments = optionalComment.get();
        User user = optionalUser.get();

        if(comments.getUser().equals(user)){
            commentRepo.delete(comments);
        }else{
            throw new Exception("User doesn't have permission to delete this comment");
        }
    }

    @Override
    public List<Comments> findCommentsByIssue(Long issueId) {
        return commentRepo.findByIssueId(issueId);
    }
}
