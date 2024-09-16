package com.projects.spring.projectmanager.service;


import com.projects.spring.projectmanager.Model.User;
import com.projects.spring.projectmanager.repository.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo repository;

    public CustomUserDetailsService(UserRepo repo) {
        this.repository = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);

        if(user==null){
            throw new UsernameNotFoundException("User not found with email" + username);
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorityList);
    }
}
