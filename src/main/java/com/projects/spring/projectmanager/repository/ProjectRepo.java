package com.projects.spring.projectmanager.repository;

import com.projects.spring.projectmanager.Model.Project;
import com.projects.spring.projectmanager.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {
   List<Project> findByNameContainingAndTeamContaining(String keyword, User teamMember);

    List<Project> findByTeamContainingOrOwner(User teamMember, User owner);

//    List<Project> findByTeamContaining(User teamMember);


}