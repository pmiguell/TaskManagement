package com.todolist.backend.controller;

import com.todolist.backend.DTO.TaskDTO;
import com.todolist.backend.entity.UserEntity;
import com.todolist.backend.service.TaskService;
import com.todolist.backend.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        List<TaskDTO> tasks = taskService.getAllTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TaskDTO>> filterTasks(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline,
            Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        List<TaskDTO> filteredTasks = taskService.filterTasks(description, category, status, deadline, userId);
        return ResponseEntity.ok(filteredTasks);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories(Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        List<String> categories = taskService.getAllCategories(userId);
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO, Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        TaskDTO createdTask = taskService.createTask(taskDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Integer id, @RequestBody TaskDTO taskDTODetails, Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        TaskDTO updatedTask = taskService.updateTask(id, taskDTODetails, userId);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/conclude/{id}")
    public ResponseEntity<TaskDTO> concludeTask(@PathVariable Integer id, Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        TaskDTO concludedTask = taskService.concludeTask(id, userId);
        return ResponseEntity.ok(concludedTask);
    }

    @PatchMapping("/undo-conclude/{id}")
    public ResponseEntity<TaskDTO> undoConcludeTask(@PathVariable Integer id, Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        TaskDTO undoConcludedTask = taskService.undoConcludeTask(id, userId);
        return ResponseEntity.ok(undoConcludedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id, Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        taskService.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTasks(Authentication authentication) {
        String userId = ((UserEntity) authentication.getPrincipal()).getId();
        taskService.deleteAllTasks(userId);
        return ResponseEntity.noContent().build();
    }
}
