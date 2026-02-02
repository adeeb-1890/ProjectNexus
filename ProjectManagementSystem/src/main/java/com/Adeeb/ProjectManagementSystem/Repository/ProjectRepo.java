package com.Adeeb.ProjectManagementSystem.Repository;

import com.Adeeb.ProjectManagementSystem.Model.Project;
import com.Adeeb.ProjectManagementSystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project , Long> {

    List<Project> findByLeader(User user);

    List<Project> findByNameContainingAndTeamContains(String partialName , User user);


    List<Project> findByTeamContainingOrLeader(User user , User Leader);
}
