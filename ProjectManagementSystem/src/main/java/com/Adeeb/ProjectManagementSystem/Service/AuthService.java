package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Repository.UserRepo;
import com.Adeeb.ProjectManagementSystem.Response.AuthResponse;
import com.Adeeb.ProjectManagementSystem.SecurityConfiguration.JwtProvider;
import com.Adeeb.ProjectManagementSystem.UserDetail.CustomUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CustomUserDetailsImpl customUserDetails;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;




    public AuthResponse RegisterUser(User user) throws Exception {

        User doesUserExists = userRepo.findByEmail(user.getEmail());

        if(doesUserExists != null){
            throw new Exception("Email already exists:");
        }

        User newuser = new User();
        newuser.setEmail(user.getEmail());
        newuser.setPassword(passwordEncoder.encode(user.getPassword()));
        newuser.setFullName(user.getFullName());
        newuser.setNumberOfProjects(0L);
        User SavedUser = userRepo.save(newuser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail() , user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("signup Success");
        authResponse.setJwt(token);

        return authResponse;
    }

    public AuthResponse LoginUser(String email, String password) {
        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login Success:");
        return authResponse;
    }

    private Authentication authenticate(String username , String password){

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if(userDetails == null)
            throw new BadCredentialsException("Invalid Username or Password");

        if(!passwordEncoder.matches(password , userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Username or Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
    }
}
