package com.todolist.backend.controller;

import com.todolist.backend.DTO.LoginRequestDTO;
import com.todolist.backend.DTO.RegisterRequestDTO;
import com.todolist.backend.DTO.ResponseDTO;
import com.todolist.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    @Autowired
    private AuthService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO request) {
        ResponseDTO response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        ResponseDTO response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }
}