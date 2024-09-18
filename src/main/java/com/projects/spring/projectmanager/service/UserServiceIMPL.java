package com.projects.spring.projectmanager.service;

import com.projects.spring.projectmanager.Model.User;
import com.projects.spring.projectmanager.config.JWTProvider;
import com.projects.spring.projectmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceIMPL implements UserService {

    private final UserRepo repo;

    public UserServiceIMPL(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {

        String email = JWTProvider.getEmailFromToken(jwt);

        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = repo.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found with email" + email);
        }
        return user;
    }

    @Override
    public User findUserById(Long id) throws Exception {

        Optional<User> user = repo.findById(id);
        if(user.isEmpty()){
            throw new Exception("User not found with id" + id);
        }
        else{
            return user.get();
        }
    }

    @Override
    public User updateUserProjectSize(User user, int number) {
        user.setProjectSize(user.getProjectSize() + number);
        return repo.save(user);
    }
}
