package com.todolist.backend.service;

import com.todolist.backend.DTO.TaskDTO;
import com.todolist.backend.exception.TaskNotFoundException;
import com.todolist.backend.model.Task;
import com.todolist.backend.repository.TaskRepository;
import com.todolist.backend.specification.TaskSpecifications;
import com.todolist.backend.utils.Status;
import com.todolist.backend.utils.TaskUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> filterTasks(String description, String category, Status status, LocalDate deadline) {
        Specification<Task> spec = Specification
                .where(TaskSpecifications.hasDescription(description))
                .and(TaskSpecifications.hasCategory(category))
                .and(TaskSpecifications.hasStatus(status))
                .and(TaskSpecifications.hasDeadline(deadline));

        return taskRepository.findAll(spec).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        task.setStatus(TaskUtils.calculateStatus(task.getDeadline()));
        Task savedTask = taskRepository.save(task);

        return convertToDTO(savedTask);
    }

    public TaskDTO updateTask(Integer id, TaskDTO taskDTODetails) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));

        existingTask.setDescription(taskDTODetails.getDescription());
        existingTask.setCategory(taskDTODetails.getCategory());
        existingTask.setDeadline(taskDTODetails.getDeadline());
        existingTask.setStatus(TaskUtils.calculateStatus(taskDTODetails.getDeadline()));

        Task updatedTask = taskRepository.save(existingTask);

        return convertToDTO(updatedTask);
    }

    public TaskDTO concludeTask(Integer id) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));

        existingTask.setStatus(Status.CONCLUIDA);

        Task concludedTask = taskRepository.save(existingTask);

        return convertToDTO(concludedTask);
    }

    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setCategory(task.getCategory());
        taskDTO.setDeadline(task.getDeadline());
        taskDTO.setStatus(task.getStatus());
        return taskDTO;
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setDescription(taskDTO.getDescription());
        task.setCategory(taskDTO.getCategory());
        task.setDeadline(taskDTO.getDeadline());
        task.setStatus(taskDTO.getStatus());
        return task;
    }
}
