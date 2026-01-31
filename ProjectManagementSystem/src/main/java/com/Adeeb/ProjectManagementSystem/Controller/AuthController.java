package com.Adeeb.ProjectManagementSystem.Controller;

import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Request.LoginRequest;
import com.Adeeb.ProjectManagementSystem.Response.AuthResponse;
import com.Adeeb.ProjectManagementSystem.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> RegisterUser(@RequestBody User user) throws Exception{
        return new ResponseEntity<>(authService.RegisterUser(user) , HttpStatus.OK);
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> LoginUser(@RequestBody LoginRequest loginRequest)throws Exception{
        return new ResponseEntity<>(authService.LoginUser(loginRequest.getEmail() , loginRequest.getPassword()) , HttpStatus.OK);

    }
}
