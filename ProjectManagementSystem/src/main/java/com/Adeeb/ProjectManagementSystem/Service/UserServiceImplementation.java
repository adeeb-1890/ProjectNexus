package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Repository.UserRepo;
import com.Adeeb.ProjectManagementSystem.SecurityConfiguration.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public User findUserProfileByJwt(String token) throws Exception {
        String email = JwtProvider.getEmailFromToken(token);

        return findUserBYEmail(email);
    }

    @Override
    public User findUserBYEmail(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        if(user == null)
            throw new Exception("User not found.");

        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> optionalUser = userRepo.findById(userId);

        if(optionalUser.isEmpty())
            throw new Exception("User not found");

        return optionalUser.get();
    }

    @Override
    public User updateUsersProjectSize(User user, Long number) {
        user.setNumberOfProjects(user.getNumberOfProjects() + number);
        return userRepo.save(user);
    }
}
