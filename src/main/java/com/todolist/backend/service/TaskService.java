package com.todolist.backend.service;

import com.todolist.backend.exception.TaskNotFoundException;
import com.todolist.backend.model.Task;
import com.todolist.backend.repository.TaskRepository;
import com.todolist.backend.utils.Status;
import com.todolist.backend.utils.TaskUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getFilteredTasks(String category, Status status, LocalDate deadline, String description) {
        return taskRepository.findFilteredTasks(category, status, deadline, description);
    }


    public Task createTask(Task task) {
        task.setStatus(TaskUtils.calculateStatus(task.getDeadline()));

        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, Task taskDetails) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));

        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setCategory(taskDetails.getCategory());
        existingTask.setDeadline(taskDetails.getDeadline());
        existingTask.setStatus(TaskUtils.calculateStatus(taskDetails.getDeadline()));

        return taskRepository.save(existingTask);
    }

    public Task concludeTask(Integer id) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));

        existingTask.setStatus(Status.CONCLUIDA);

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

}
