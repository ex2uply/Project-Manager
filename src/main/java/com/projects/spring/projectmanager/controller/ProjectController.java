package com.projects.spring.projectmanager.controller;


import com.projects.spring.projectmanager.Model.*;
import com.projects.spring.projectmanager.repository.InvitationRequest;
import com.projects.spring.projectmanager.response.MessageResponse;
import com.projects.spring.projectmanager.service.InvitationService;
import com.projects.spring.projectmanager.service.ProjectService;
import com.projects.spring.projectmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    private final UserService userService;

    private final InvitationService invitationService;

    public ProjectController(ProjectService projectService, UserService userService, InvitationService invitationService) {
        this.projectService = projectService;
        this.userService = userService;
        this.invitationService = invitationService;
    }

    //get all projects
    @GetMapping("/all")
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false)String category,
            @RequestParam(required = false)String tag,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);         //to find which user is requesting the data the jwt string will contain tokens, finding the user details
        List<Project> projects = projectService.getProjectByTeam(user,category,tag);


        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    //get project by id
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable("id")Long ProjectId,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(ProjectId);


        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    //create project
    @PostMapping("/create")
    public ResponseEntity<Project> createProject(
            @RequestBody Project project,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project newProject = projectService.createProject(project,user);

        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    //update project
    @PatchMapping("/{id}")
    public ResponseEntity<Project> updateProject(
            @RequestBody Project updatedProject,
            @PathVariable("id")Long projectId,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.updateProject(updatedProject,projectId);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    //delete project
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable("id")Long projectId,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId,user.getId());

        MessageResponse message = new MessageResponse("Project Deleted");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //add user to project
    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false)String keyword,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);         //to find which user is requesting the data the jwt string will contain tokens, finding the user details
        List<Project> projects = projectService.searchProjects(keyword,user);  //searching projects for a given user using the keywords;

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("{id}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable("id")Long projectId,     // just a reminder that -> need to specify in brackets the variable name I chose is different from the path variable name in url
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectId);

        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
            @RequestBody InvitationRequest request,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(request.getEmail(),request.getProjectId());

        MessageResponse message = new MessageResponse("User Invitation Sent");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptProject(
            @RequestParam String token,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation= invitationService.acceptInvitation(token,user.getId());
        projectService.addUserToProject(invitation.getProjectId(),user.getId());

        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }


}
