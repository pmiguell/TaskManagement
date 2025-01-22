package com.todolist.backend.service;

import com.todolist.backend.DTO.TaskDTO;
import com.todolist.backend.exception.TaskNotFoundException;
import com.todolist.backend.model.Task;
import com.todolist.backend.model.User;
import com.todolist.backend.repository.TaskRepository;
import com.todolist.backend.repository.UserRepository;
import com.todolist.backend.specification.TaskSpecifications;
import com.todolist.backend.utils.Status;
import com.todolist.backend.utils.TaskUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskDTO> getAllTasks(String userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> getAllCategories(String userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(Task::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<TaskDTO> filterTasks(String description, String category, Status status, LocalDate deadline, String userId) {
        Specification<Task> spec = Specification
                .where(TaskSpecifications.hasDescription(description))
                .and(TaskSpecifications.hasCategory(category))
                .and(TaskSpecifications.hasStatus(status))
                .and(TaskSpecifications.hasDeadline(deadline))
                .and(TaskSpecifications.hasUserId(userId));

        return taskRepository.findAll(spec).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO createTask(TaskDTO taskDTO, String userId) {
        Task task = convertToEntity(taskDTO);
        task.setStatus(TaskUtils.calculateStatus(task.getDeadline()));

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return convertToDTO(savedTask);
    }

    public TaskDTO updateTask(Integer id, TaskDTO taskDTODetails, String userId) {
        Task existingTask = taskRepository.findByIdAndUserId(id, userId);

        existingTask.setDescription(taskDTODetails.getDescription());
        existingTask.setCategory(taskDTODetails.getCategory());
        existingTask.setDeadline(taskDTODetails.getDeadline());
        existingTask.setStatus(TaskUtils.calculateStatus(taskDTODetails.getDeadline()));

        Task updatedTask = taskRepository.save(existingTask);

        return convertToDTO(updatedTask);
    }

    public TaskDTO concludeTask(Integer id, String userId) {
        Task existingTask = taskRepository.findByIdAndUserId(id, userId);

        existingTask.setStatus(Status.COMPLETED);

        Task concludedTask = taskRepository.save(existingTask);

        return convertToDTO(concludedTask);
    }

    public TaskDTO undoConcludeTask(Integer id, String userId) {
        Task existingTask = taskRepository.findByIdAndUserId(id, userId);

        existingTask.setStatus(TaskUtils.calculateStatus(existingTask.getDeadline()));

        Task undoConcludedTask = taskRepository.save(existingTask);

        return convertToDTO(undoConcludedTask);
    }

    public void deleteTask(Integer id, String userId) {
        Task task = taskRepository.findByIdAndUserId(id, userId);
        taskRepository.delete(task);
    }

    @Transactional
    public void deleteAllTasks(String userId) {
        taskRepository.deleteByUserId(userId);
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
