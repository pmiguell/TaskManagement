package com.todolist.backend.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TaskUtils {
    public static Status calculateStatus(LocalDate deadline) {
        LocalDate currentDate = LocalDate.now();

        if (currentDate.isBefore(deadline) || currentDate.isEqual(deadline)) {
            return Status.PENDING;
        } else {
            return Status.OVERDUE;
        }
    }
}
