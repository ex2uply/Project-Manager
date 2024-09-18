package com.projects.spring.projectmanager.service;


import com.projects.spring.projectmanager.Model.User;

public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(Long id) throws Exception;

    User updateUserProjectSize(User user,int number);





}
