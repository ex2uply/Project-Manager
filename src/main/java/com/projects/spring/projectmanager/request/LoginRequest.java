package com.projects.spring.projectmanager.request;


import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
