package com.todolist.backend.controller;

import com.todolist.backend.DTO.TaskDTO;
import com.todolist.backend.entity.UserEntity;
import com.todolist.backend.service.TaskService;
import com.todolist.backend.utils.TaskFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Tasks", description = "User task management")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Gets all tasks from the authenticated user")
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        List<TaskDTO> tasks = taskService.getAllTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Filters tasks based on entered criteria")
    @GetMapping("/filter")
    public ResponseEntity<List<TaskDTO>> filterTasks(TaskFilter taskFilter, Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        List<TaskDTO> filteredTasks = taskService.filterTasks(taskFilter, userId);
        return ResponseEntity.ok(filteredTasks);
    }

    @Operation(summary = "Gets all user task categories")
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories(Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        List<String> categories = taskService.getAllCategories(userId);
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Creates a new task")
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO, Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        TaskDTO createdTask = taskService.createTask(taskDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @Operation(summary = "Updates an existing task")
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Integer id, @RequestBody TaskDTO taskDTODetails, Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        TaskDTO updatedTask = taskService.updateTask(id, taskDTODetails, userId);
        return ResponseEntity.ok(updatedTask);
    }

    @Operation(summary = "Changes the task status to COMPLETED")
    @PatchMapping("/conclude/{id}")
    public ResponseEntity<TaskDTO> concludeTask(@PathVariable Integer id, Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        TaskDTO concludedTask = taskService.concludeTask(id, userId);
        return ResponseEntity.ok(concludedTask);
    }

    @Operation(summary = "Undoes the completion of a task")
    @PatchMapping("/undo-conclude/{id}")
    public ResponseEntity<TaskDTO> undoConcludeTask(@PathVariable Integer id, Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        TaskDTO undoConcludedTask = taskService.undoConcludeTask(id, userId);
        return ResponseEntity.ok(undoConcludedTask);
    }

    @Operation(summary = "Deletes a task by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id, Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        taskService.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletes all tasks for the authenticated user")
    @DeleteMapping
    public ResponseEntity<Void> deleteAllTasks(Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        taskService.deleteAllTasks(userId);
        return ResponseEntity.noContent().build();
    }
}
