package com.projects.spring.projectmanager.repository;

import com.projects.spring.projectmanager.Model.Project;
import com.projects.spring.projectmanager.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project,Long> {

//   List<Project> findByOwner(User user);

   List<Project> findByNameContainingAndTeamContains(String name, User user);

//   @Query("SELECT p FROM Project p join p.team t WHERE t=:user")
//   List<Project> findProjectByTeam(@Param("user") User user);

   List<Project> findByTeamContainingOrOwner(User member, User owner);
}
