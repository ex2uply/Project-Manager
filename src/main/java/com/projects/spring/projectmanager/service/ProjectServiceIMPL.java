package com.projects.spring.projectmanager.service;

import com.projects.spring.projectmanager.Model.Chat;
import com.projects.spring.projectmanager.Model.Project;
import com.projects.spring.projectmanager.Model.User;
import com.projects.spring.projectmanager.repository.ProjectRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectServiceIMPL implements ProjectService{

    private final ProjectRepo repo;

    private final UserService userService;

    public final ChatService chatService;

    public ProjectServiceIMPL(ProjectRepo repo, UserService userService, ChatService chatService) {
        this.repo = repo;
        this.userService = userService;
        this.chatService = chatService;
    }


    @Override
    public List<Project> getAllProjects(User user) throws Exception {
        return repo.findByTeamContainingOrOwner(user,user);
    }

    @Override
    public Project createProject(Project project, User user) throws Exception {
        // Create a new project
        Project newProject = new Project();
        newProject.setOwner(user);
        newProject.setName(project.getName());
        newProject.setTags(project.getTags());
        newProject.setCategory(project.getCategory());
        newProject.setDescription(project.getDescription());
        newProject.getTeam().add(user);
        // Save the project
        Project savedProject = repo.save(newProject);


        //Add chat to the project
        Chat chat = new Chat();
        chat.setProject(savedProject);
        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);


        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = repo.findByTeamContainingOrOwner(user,user);

        if(category!=null){
            projects = projects.stream().filter(project -> project.getCategory().equals(category)).toList();
        }

        if(tag!=null){
            projects = projects.stream().filter(project -> project.getTags().contains(tag)).toList();
        }

        return projects;
    }


    @Override
    public Project getProjectById(Long Id) throws Exception {
        Optional<Project> project = repo.findById(Id);

        if(project.isEmpty()){
            throw new Exception("Project not found");
        }
        return project.get();
    }


    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        Optional<Project> project = repo.findById(projectId);
        //difference
        if(project.isEmpty()){
            throw new Exception("Project not found");
        }

        if(!Objects.equals(project.get().getOwner().getId(), userId)){
            throw new Exception("You are not the owner of this project");
        }

        repo.deleteById(projectId);

    }


    @Override
    public Project updateProject(Project updatedProject, Long Id) throws Exception {
        Project project = getProjectById(Id);

        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());

        return repo.save(project);

    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(project.getTeam().contains(user)){
            throw new Exception("User already in the project");
        }
        project.getChat().getUser().add(user);
        project.getTeam().add(user);
        repo.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(!project.getTeam().contains(user)){
            throw new Exception("User not in the project");
        }
        project.getChat().getUser().remove(user);
        project.getTeam().remove(user);
        repo.save(project);

    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        return project.getChat();

    }



    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {
        if (keyword == null || keyword.isEmpty()) {
            return repo.findByTeamContainingOrOwner(user, user);
        }
        return repo.findByNameContainingAndTeamContaining(keyword, user);
    }


}
