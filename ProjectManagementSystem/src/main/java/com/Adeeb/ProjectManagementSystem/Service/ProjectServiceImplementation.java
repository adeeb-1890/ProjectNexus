package com.Adeeb.ProjectManagementSystem.Service;

import com.Adeeb.ProjectManagementSystem.Model.Chat;
import com.Adeeb.ProjectManagementSystem.Model.Project;
import com.Adeeb.ProjectManagementSystem.Model.User;
import com.Adeeb.ProjectManagementSystem.Repository.ProjectRepo;
import com.Adeeb.ProjectManagementSystem.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImplementation implements ProjectService{

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project newProject = new Project();

        newProject.setLeader(user);
        newProject.setTags(project.getTags());
        newProject.setName(project.getName());
        newProject.setCategory(project.getCategory());
        newProject.setDescription(project.getDescription());
        newProject.getTeam().add(user);

        Project savedProject = projectRepo.save(newProject);

        Chat newChat = new Chat();
        newChat.setProject(savedProject);

        Chat projectChat = chatService.createChat(newChat);
        savedProject.setChat(newChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepo.findByTeamContainingOrLeader(user , user);

        if(category != null) {
            projects = projects.stream().filter(project -> project.getCategory().equals(category))
                    .collect(Collectors.toList());
        }

        if(tag != null) {
            projects = projects.stream().filter(project -> project.getCategory().equals(tag))
                    .collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> optionalProject = projectRepo.findById(projectId);

        if(optionalProject.isEmpty()){
            throw new Exception("Project not found");
        }

        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {

        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (!project.getLeader().getId().equals(user.getId())) {
            throw new Exception("Only project owner can delete the project");
        }

        projectRepo.delete(project);
    }


    @Override
    public Project updateProject(Project updateProject, Long id) throws Exception {
        Project project = getProjectById(id);

        project.setName(updateProject.getName());
        project.setDescription(updateProject.getDescription());
        project.setTags(updateProject.getTags());

        return projectRepo.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }

        projectRepo.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception{
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }
        projectRepo.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);

        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {
        String partialName = "%" + keyword + "%";

        List<Project> projects = projectRepo.findByNameContainingAndTeamContains(keyword , user);

        return projects;
    }
}
