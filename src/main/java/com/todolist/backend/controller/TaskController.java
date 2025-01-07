package com.todolist.backend.controller;

import com.todolist.backend.DTO.TaskDTO;
import com.todolist.backend.model.Task;
import com.todolist.backend.service.TaskService;
import com.todolist.backend.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/filter")
    public List<TaskDTO> getFilteredTasks(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) LocalDate deadline,
            @RequestParam(required = false) String description) {

        return taskService.getFilteredTasks(category, status, deadline, description);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Integer id, @RequestBody TaskDTO taskDTODetails) {
        TaskDTO updatedTask = taskService.updateTask(id, taskDTODetails);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/conclude/{id}")
    public ResponseEntity<TaskDTO> concludeTask(@PathVariable Integer id) {
        TaskDTO concludedTask = taskService.concludeTask(id);
        return ResponseEntity.ok(concludedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
