package com.todolist.backend.repository;

import com.todolist.backend.model.Task;
import com.todolist.backend.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByDescriptionContainingAndCategoryContainingAndStatusAndDeadline(String description, String category, Status status, LocalDate deadline);
}
