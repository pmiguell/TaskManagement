package com.todolist.backend.controller;

import com.todolist.backend.model.Task;
import com.todolist.backend.service.TaskService;
import com.todolist.backend.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/filter")
    public List<Task> getFilteredTasks(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) LocalDate deadline,
            @RequestParam(required = false) String description) {

        category = (category != null && category.isEmpty()) ? null : category;
        description = (description != null && description.isEmpty()) ? null : description;

        return taskService.getFilteredTasks(category, status, deadline, description);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody Task taskDetails) {
        return taskService.updateTask(id, taskDetails);
    }

    @PutMapping("/conclude/{id}")
    public Task concludeTask(@PathVariable Integer id) {
        return taskService.concludeTask(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }
}
