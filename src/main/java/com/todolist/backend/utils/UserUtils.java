package com.todolist.backend.utils;

import com.todolist.backend.entity.UserEntity;
import com.todolist.backend.exception.UserNotFoundException;
import com.todolist.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    @Autowired
    private UserRepository userRepository;

    public UserEntity getValidUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

}
