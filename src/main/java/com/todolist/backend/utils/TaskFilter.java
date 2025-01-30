package com.todolist.backend.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskFilter {
    private String description;
    private String category;
    private Status status;
    private LocalDate deadline;
}
