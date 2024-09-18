package com.projects.spring.projectmanager.service;

import com.projects.spring.projectmanager.Model.Chat;
import com.projects.spring.projectmanager.Model.Project;
import com.projects.spring.projectmanager.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProjectService {

    Project createProject(Project project, User user)throws Exception;

    List<Project> getProjectByTeam(User user, String category , String tag) throws Exception;

    Project getProjectById(Long Id) throws Exception;

    void deleteProject(Long projectId, Long userId) throws Exception;

    Project updateProject(Project updatedProject, Long userId) throws Exception;

    void addUserToProject(Long projectId, Long userId) throws Exception;

    void removeUserFromProject(Long projectId, Long userId) throws Exception;

    Chat getChatByProjectId(Long projectId) throws Exception;

    List<Project> searchProjects(String keyword,User user) throws Exception;



}
