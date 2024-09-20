package com.projects.spring.projectmanager.service;

import com.projects.spring.projectmanager.Model.Issues;
import com.projects.spring.projectmanager.Model.Project;
import com.projects.spring.projectmanager.Model.User;
import com.projects.spring.projectmanager.repository.IssueRepo;
import com.projects.spring.projectmanager.repository.ProjectRepo;
import com.projects.spring.projectmanager.repository.UserRepo;
import com.projects.spring.projectmanager.request.IssuesRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class IssueServiceIMPl implements IssueService{

    private final ProjectService projectService;

    final
    ProjectRepo projectRepo;

    final IssueRepo issueRepo;

    private final UserService userService;

    public IssueServiceIMPl(ProjectRepo projectRepo, IssueRepo issueRepo, ProjectService projectService, UserRepo userRepo, UserService userService) {
        this.projectRepo = projectRepo;
        this.issueRepo = issueRepo;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public Optional<Issues> getIssueById(Long issueId) throws Exception {
        Optional<Issues> issue = issueRepo.findById(issueId);
        if(issue.isEmpty()){
            throw new Exception("No issues found with id: "+issueId);
        }
        return issue;
    }

    @Override
    public List<Issues> getIssuesByProjectId(Long projectId) throws Exception {

        return issueRepo.findByProjectId(projectId);
    }

    @Override
    public Issues createIssue(IssuesRequest issuesRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issuesRequest.getProjectId());
        Issues issues = new Issues();
        //all six instance variables of issue request are set
        issues.setTitle(issuesRequest.getTitle());
        issues.setDescription(issuesRequest.getDescription());
        issues.setStatus(issuesRequest.getStatus());
        issues.setProject(project);
        issues.setPriority(issuesRequest.getPriority());
        issues.setDueDate(issuesRequest.getDueDate());

        return issueRepo.save(issues);
    }

    @Override
    public void deleteIssue(Long id, Long userId) throws Exception {
        Optional<Issues> issue = issueRepo.findById(id);
        if(issue.isEmpty()){
            throw new Exception("No issues found with id: "+id);
        }
        if(!Objects.equals(issue.get().getProject().getOwner().getId(), userId)){
            throw new Exception("You are not authorized to delete this issue");
        }
        issueRepo.deleteById(id);
    }

    @Override
    public Issues addUserToIssue(Long issueId, Long userId) throws Exception {
        Optional<Issues> issue = issueRepo.findById(issueId);
        if(issue.isEmpty()){
            throw new Exception("No issues found with id: "+issueId);
        }
        User user = userService.findUserById(userId);
        issue.get().setAssignee(user);
        return issueRepo.save(issue.get());
    }

    @Override
    public Issues updateStatus(Long issueId, String status) throws Exception {
        Optional<Issues> issue = issueRepo.findById(issueId);
        if(issue.isEmpty()){
            throw new Exception("No issues found with id: "+issueId);
        }
        issue.get().setStatus(status);
        return issueRepo.save(issue.get());
    }
}
