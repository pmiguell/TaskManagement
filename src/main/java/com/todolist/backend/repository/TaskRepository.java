package com.todolist.backend.repository;

import com.todolist.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {
    List<Task> findByUserId(String userId);
    void deleteByUserId(String userId);
    Task findByIdAndUserId(Integer id, String userId);
}