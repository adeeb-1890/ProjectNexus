package com.Adeeb.ProjectManagementSystem.Controller;

import com.Adeeb.ProjectManagementSystem.Model.Comments;
import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Request.CreateCommentRequest;
import com.Adeeb.ProjectManagementSystem.Response.MessageResponse;
import com.Adeeb.ProjectManagementSystem.Service.CommentsService;
import com.Adeeb.ProjectManagementSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comments> createComment(@RequestBody CreateCommentRequest request ,
                                                  @RequestHeader("Authorization")String jwtToken) throws Exception{
        User user = userService.findUserProfileByJwt(jwtToken);
        Comments createdComment = commentsService.createCommment(request.getIssueId() ,
                user.getId() , request.getContent());

        return new ResponseEntity<>(createdComment , HttpStatus.CREATED);
    }

    @DeleteMapping("/commentId")
    public ResponseEntity<MessageResponse> deleteCommentById(@PathVariable Long commentId ,
                                                             @RequestHeader("Authorization")String jwtToken) throws Exception{
        User user = userService.findUserProfileByJwt(jwtToken);
        commentsService.deleteComment(commentId , user.getId());
        MessageResponse message = new MessageResponse();
        message.setMessageResponse("Comment deleted Succesfully");

        return new ResponseEntity<>(message , HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comments>> getCommentsByIssueId(@PathVariable Long issueId ,
                                                               @RequestHeader("Authorization")String jwtToken) throws Exception{
        List<Comments> comments = commentsService.findCommentsByIssue(issueId);

        return new ResponseEntity<>(comments , HttpStatus.OK);
    }
}
