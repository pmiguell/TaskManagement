package com.todolist.backend.service;

import com.todolist.backend.DTO.LoginRequestDTO;
import com.todolist.backend.DTO.LoginResponseDTO;
import com.todolist.backend.DTO.RegisterRequestDTO;
import com.todolist.backend.DTO.RegisterResponseDTO;
import com.todolist.backend.entity.UserEntity;
import com.todolist.backend.exception.InvalidCredentialsException;
import com.todolist.backend.exception.UserAlreadyExistsException;
import com.todolist.backend.exception.UserNotFoundException;
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

    public LoginResponseDTO login(LoginRequestDTO request) {
        UserEntity userEntity = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (passwordEncoder.matches(request.password(), userEntity.getPassword())) {
            String token = tokenService.generateToken(userEntity);
            return new LoginResponseDTO(userEntity.getEmail(), token);
        }

        throw new InvalidCredentialsException("Invalid credentials");
    }

    public RegisterResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode((request.password())))
                .build();

        userRepository.save(userEntity);

        String token = tokenService.generateToken(userEntity);

        return new RegisterResponseDTO(userEntity.getEmail());
    }
}