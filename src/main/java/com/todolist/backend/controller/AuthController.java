package com.todolist.backend.controller;

import com.todolist.backend.DTO.LoginRequestDTO;
import com.todolist.backend.DTO.LoginResponseDTO;
import com.todolist.backend.DTO.RegisterRequestDTO;
import com.todolist.backend.DTO.RegisterResponseDTO;
import com.todolist.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    private final AuthService authenticationService;

    @Operation(summary = "Log in", description = "Authenticates a user by email and password")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register a new user", description = "Creates a new user account")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        RegisterResponseDTO response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }
}