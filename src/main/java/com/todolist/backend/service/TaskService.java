package com.todolist.backend.service;

import com.todolist.backend.DTO.TaskDTO;
import com.todolist.backend.entity.TaskEntity;
import com.todolist.backend.entity.UserEntity;
import com.todolist.backend.repository.TaskRepository;
import com.todolist.backend.specification.TaskSpecifications;
import com.todolist.backend.utils.Status;
import com.todolist.backend.utils.TaskUtils;
import com.todolist.backend.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserUtils userUtils;

    public List<TaskDTO> getAllTasks(String userId) {
        UserEntity userEntity = userUtils.getValidUser(userId);
        return taskRepository.findByUserId(userEntity.getId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> getAllCategories(String userId) {
        UserEntity userEntity = userUtils.getValidUser(userId);
        return taskRepository.findByUserId(userEntity.getId()).stream()
                .map(TaskEntity::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    public TaskDTO createTask(TaskDTO taskDTO, String userId) {
        TaskEntity taskEntity = convertToEntity(taskDTO);
        taskEntity.setStatus(TaskUtils.calculateStatus(taskEntity.getDeadline()));

        UserEntity userEntity = userUtils.getValidUser(userId);
        taskEntity.setUser(userEntity);

        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);

        return convertToDTO(savedTaskEntity);
    }

    public List<TaskDTO> filterTasks(String description, String category, Status status, LocalDate deadline, String userId) {
        UserEntity userEntity = userUtils.getValidUser(userId);

        Specification<TaskEntity> spec = Specification
                .where(TaskSpecifications.hasDescription(description))
                .and(TaskSpecifications.hasCategory(category))
                .and(TaskSpecifications.hasStatus(status))
                .and(TaskSpecifications.hasDeadline(deadline))
                .and(TaskSpecifications.hasUserId(userEntity.getId()));

        return taskRepository.findAll(spec).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO updateTask(Integer id, TaskDTO taskDTODetails, String userId) {
        UserEntity userEntity = userUtils.getValidUser(userId);
        TaskEntity existingTaskEntity = taskRepository.findByIdAndUserId(id, userEntity.getId());

        existingTaskEntity.setDescription(taskDTODetails.getDescription());
        existingTaskEntity.setCategory(taskDTODetails.getCategory());
        existingTaskEntity.setDeadline(taskDTODetails.getDeadline());
        existingTaskEntity.setStatus(TaskUtils.calculateStatus(taskDTODetails.getDeadline()));

        TaskEntity updatedTaskEntity = taskRepository.save(existingTaskEntity);

        return convertToDTO(updatedTaskEntity);
    }

    public TaskDTO concludeTask(Integer id, String userId) {
        UserEntity userEntity = userUtils.getValidUser(userId);
        TaskEntity existingTaskEntity = taskRepository.findByIdAndUserId(id, userEntity.getId());

        existingTaskEntity.setStatus(Status.COMPLETED);

        TaskEntity concludedTaskEntity = taskRepository.save(existingTaskEntity);

        return convertToDTO(concludedTaskEntity);
    }

    public TaskDTO undoConcludeTask(Integer id, String userId) {
        UserEntity userEntity = userUtils.getValidUser(userId);
        TaskEntity existingTaskEntity = taskRepository.findByIdAndUserId(id, userEntity.getId());

        existingTaskEntity.setStatus(TaskUtils.calculateStatus(existingTaskEntity.getDeadline()));

        TaskEntity undoConcludedTaskEntity = taskRepository.save(existingTaskEntity);

        return convertToDTO(undoConcludedTaskEntity);
    }

    public void deleteTask(Integer id, String userId) {
        UserEntity userEntity = userUtils.getValidUser(userId);
        TaskEntity taskEntity = taskRepository.findByIdAndUserId(id, userEntity.getId());
        taskRepository.delete(taskEntity);
    }

    @Transactional
    public void deleteAllTasks(String userId) {
        UserEntity userEntity = userUtils.getValidUser(userId);
        taskRepository.deleteByUserId(userEntity.getId());
    }

    private TaskDTO convertToDTO(TaskEntity taskEntity) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskEntity.getId());
        taskDTO.setDescription(taskEntity.getDescription());
        taskDTO.setCategory(taskEntity.getCategory());
        taskDTO.setDeadline(taskEntity.getDeadline());
        taskDTO.setStatus(taskEntity.getStatus());
        return taskDTO;
    }

    private TaskEntity convertToEntity(TaskDTO taskDTO) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setDescription(taskDTO.getDescription());
        taskEntity.setCategory(taskDTO.getCategory());
        taskEntity.setDeadline(taskDTO.getDeadline());
        taskEntity.setStatus(taskDTO.getStatus());
        return taskEntity;
    }
}
