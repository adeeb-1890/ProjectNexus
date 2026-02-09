package com.Adeeb.ProjectManagementSystem.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data@AllArgsConstructor
@NoArgsConstructor
public class IssueRequest {
    private String title;
    private String description;
    private String status;
    private Long projectFk;
    private String priority;
    private LocalDate dueDate;
}
