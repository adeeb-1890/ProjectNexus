package com.Adeeb.ProjectManagementSystem.Repository;

import com.Adeeb.ProjectManagementSystem.Model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepo extends JpaRepository<Issue, Long> {

    public List<Issue> findByProjectId(Long id);
}
