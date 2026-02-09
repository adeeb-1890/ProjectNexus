package com.Adeeb.ProjectManagementSystem.DTO;

import com.Adeeb.ProjectManagementSystem.Model.Project;
import com.Adeeb.ProjectManagementSystem.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long projectFk;
    private String priority;
    private LocalDate dueDate;
    private List<String> tags = new ArrayList<>();

    private Project project;

    private User assignee;
}
