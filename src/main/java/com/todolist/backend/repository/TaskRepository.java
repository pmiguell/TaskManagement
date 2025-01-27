package com.todolist.backend.repository;

import com.todolist.backend.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer>, JpaSpecificationExecutor<TaskEntity> {
    List<TaskEntity> findByUserId(String userId);
    void deleteByUserId(String userId);
    TaskEntity findByIdAndUserId(Integer id, String userId);
}