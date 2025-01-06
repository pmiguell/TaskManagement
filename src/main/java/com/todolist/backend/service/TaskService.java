package com.todolist.backend.service;

import com.todolist.backend.model.Task;
import com.todolist.backend.repository.TaskRepository;
import com.todolist.backend.utils.Status;
import com.todolist.backend.utils.TaskUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        task.setStatus(TaskUtils.calculateStatus(task.getDeadline()));

        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, Task taskDetails) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setCategory(taskDetails.getCategory());
        existingTask.setDeadline(taskDetails.getDeadline());
        existingTask.setStatus(TaskUtils.calculateStatus(taskDetails.getDeadline()));

        return taskRepository.save(existingTask);
    }

    public Task concludeTask(Integer id) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        existingTask.setStatus(Status.CONCLUIDA);
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    public List<Task> searchTasks(String description, String category, Status status, LocalDate deadline) {
        return taskRepository.findByDescriptionContainingAndCategoryContainingAndStatusAndDeadline(description, category, status, deadline);
    }
}
