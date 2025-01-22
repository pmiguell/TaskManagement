package com.todolist.backend.controller;

import com.todolist.backend.model.User;
import com.todolist.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserNameById(@PathVariable String id, Authentication authentication) {
        String userId = ((User) authentication.getPrincipal()).getId();
        String name = userService.getUserNameById(userId);
        return ResponseEntity.ok(name);
    }
}
