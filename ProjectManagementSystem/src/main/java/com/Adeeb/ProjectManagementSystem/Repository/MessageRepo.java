package com.Adeeb.ProjectManagementSystem.Repository;

import com.Adeeb.ProjectManagementSystem.Model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Messages, Long> {
    List<Messages> findByChatIdOrderByCreatedArtAsc(Long chatId);
}
