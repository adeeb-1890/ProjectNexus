package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.User;

public interface UserService {

    User findUserProfileByJwt(String token) throws Exception;

    User findUserBYEmail(String email) throws Exception;

    User findUserById(Long userId) throws Exception;

    User updateUsersProjectSize(User user , Long number);
}
