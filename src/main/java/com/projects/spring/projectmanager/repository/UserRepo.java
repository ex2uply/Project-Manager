package com.projects.spring.projectmanager.repository;

import com.projects.spring.projectmanager.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {


    public User findByEmail(String email);
}
