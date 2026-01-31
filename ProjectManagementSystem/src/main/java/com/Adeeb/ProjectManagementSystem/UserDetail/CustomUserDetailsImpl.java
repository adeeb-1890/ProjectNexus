package com.Adeeb.ProjectManagementSystem.UserDetail;

import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Repository.UserRepo;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // check if User is present in DB or not
        User user  = userRepo.findByEmail(username);

        if(user == null)
            throw new UsernameNotFoundException("User not found with email: " +username);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.
                User(user.getEmail() , user.getPassword() , grantedAuthorities);
    }
}
