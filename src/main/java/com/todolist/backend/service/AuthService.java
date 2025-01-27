package com.todolist.backend.service;

import com.todolist.backend.DTO.LoginRequestDTO;
import com.todolist.backend.DTO.RegisterRequestDTO;
import com.todolist.backend.DTO.ResponseDTO;
import com.todolist.backend.entity.UserEntity;
import com.todolist.backend.repository.UserRepository;
import com.todolist.backend.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public ResponseDTO login(LoginRequestDTO request) {
        UserEntity userEntity = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(request.password(), userEntity.getPassword())) {
            String token = tokenService.generateToken(userEntity);
            return new ResponseDTO(userEntity.getEmail(), token);
        }
        throw new RuntimeException("Invalid credentials");
    }

    public ResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setName(request.name());
        userEntity.setEmail(request.email());
        userEntity.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(userEntity);
        String token = tokenService.generateToken(userEntity);
        return new ResponseDTO(userEntity.getName(), token);
    }
}