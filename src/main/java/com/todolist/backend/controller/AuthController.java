package com.todolist.backend.controller;

import com.todolist.backend.DTO.LoginRequestDTO;
import com.todolist.backend.DTO.LoginResponseDTO;
import com.todolist.backend.DTO.RegisterRequestDTO;
import com.todolist.backend.DTO.RegisterResponseDTO;
import com.todolist.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        RegisterResponseDTO response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }
}