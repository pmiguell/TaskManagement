package com.todolist.backend.DTO;

import com.todolist.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private Integer id;
    private String description;
    private String category;
    private LocalDate deadline;
    private Status status;
    private String userId;
}