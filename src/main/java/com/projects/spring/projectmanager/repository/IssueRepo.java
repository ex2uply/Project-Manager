package com.projects.spring.projectmanager.repository;

import com.projects.spring.projectmanager.Model.Issues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepo extends JpaRepository<Issues, Long> {

     public List<Issues> findByProjectId(Long projectId);
}
