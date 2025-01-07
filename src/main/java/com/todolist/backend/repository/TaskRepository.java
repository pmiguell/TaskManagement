package com.todolist.backend.repository;

import com.todolist.backend.model.Task; import com.todolist.backend.utils.Status; import org.springframework.data.jpa.repository.JpaRepository; import org.springframework.data.jpa.repository.Query; import org.springframework.data.repository.query.Param; import org.springframework.stereotype.Repository;

import java.time.LocalDate; import java.util.List;

@Repository public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t FROM Task t " +
            "WHERE " + "(:category IS NULL OR :category = '' OR t.category = :category) " +
            "AND " + "(:status IS NULL OR t.status = :status) " +
            "AND " + "(:deadline IS NULL OR t.deadline = :deadline) " +
            "AND " + "(:description IS NULL OR :description = '' OR LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%')))")
    List<Task> findFilteredTasks(@Param("category") String category, @Param("status") Status status, @Param("deadline") LocalDate deadline, @Param("description") String description);

}