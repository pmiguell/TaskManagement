package com.todolist.backend.utils;

import com.todolist.backend.entity.TaskEntity;
import com.todolist.backend.entity.UserEntity;
import com.todolist.backend.exception.UnauthorizedAccessException;
import com.todolist.backend.exception.UserNotFoundException;
import com.todolist.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;

    public UserEntity getValidUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public static void validateTaskOwnership(String userId, TaskEntity task) {
        if (!task.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("You don't have permission to access this task");
        }
    }
}
