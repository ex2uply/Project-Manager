package com.projects.spring.projectmanager.controller;

import com.projects.spring.projectmanager.Model.IssueDTO;
import com.projects.spring.projectmanager.Model.Issues;
import com.projects.spring.projectmanager.Model.User;
import com.projects.spring.projectmanager.request.IssuesRequest;
import com.projects.spring.projectmanager.response.MessageResponse;
import com.projects.spring.projectmanager.service.IssueService;
import com.projects.spring.projectmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    final
    IssueService issueService;
    final
    UserService userService;

    public IssueController(IssueService issueService, UserService userService) {
        this.issueService = issueService;
        this.userService = userService;
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<Issues> getIssueById(@PathVariable Long issueId) throws Exception {
        Issues issues = issueService.getIssueById(issueId).orElseThrow(() -> new Exception("No issues found with id: " + issueId));
        return ResponseEntity.ok(issues);
    }

    @RequestMapping("/project/{projectId}")
    public ResponseEntity<List<Issues>> getIssuesByProjectId(@PathVariable Long projectId) throws Exception {
        return ResponseEntity.ok(issueService.getIssuesByProjectId(projectId));
    }

    @PostMapping("/create")
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssuesRequest issues,
                                                @RequestHeader("Authorization") String token)
            throws Exception {
        if(issues.getTitle().isEmpty() || issues.getDescription().isEmpty() || issues.getDueDate() == null) {
            throw new Exception("Title, Description and Due Date are required");
        }


        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());

        Issues createIssues = issueService.createIssue(issues, user);
        IssueDTO issueDTO = new IssueDTO();

        issueDTO.setDescription(createIssues.getDescription());
        issueDTO.setDueDate(createIssues.getDueDate());
        issueDTO.setId(createIssues.getId());
        issueDTO.setPriority(createIssues.getPriority());
        issueDTO.setStatus(createIssues.getStatus());
        issueDTO.setTitle(createIssues.getTitle());
        issueDTO.setProject(createIssues.getProject());
        issueDTO.setAssignee(createIssues.getAssignee());
        issueDTO.setTags(createIssues.getTags());

        return ResponseEntity.ok(issueDTO);

    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId,
                                                    @RequestHeader("Authorization") String token) throws Exception {
        User tokenUser = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId, tokenUser.getId());

        MessageResponse response = new MessageResponse();
        response.setMessage("Issue deleted successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issues> addUserToIssue(@PathVariable Long issueId,
                                                 @PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(issueService.addUserToIssue(issueId, userId));
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issues> updateStatus(@PathVariable Long issueId,
                                               @PathVariable String status) throws Exception {
        return ResponseEntity.ok(issueService.updateStatus(issueId, status));
    }
}
