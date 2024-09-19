package com.projects.spring.projectmanager.service;

import com.projects.spring.projectmanager.Model.Issues;
import com.projects.spring.projectmanager.Model.User;
import com.projects.spring.projectmanager.request.IssuesRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Optional<Issues> getIssueById(Long id) throws Exception;

    List<Issues> getIssuesByProjectId(Long projectId) throws Exception;

    Issues createIssue(IssuesRequest issuesRequest, User user) throws Exception;

    void deleteIssue(Long id,Long userId) throws Exception;

    Issues addUserToIssue(Long issueId, Long userId) throws Exception;

    Issues updateStatus(Long issueId, String status) throws Exception;
}
