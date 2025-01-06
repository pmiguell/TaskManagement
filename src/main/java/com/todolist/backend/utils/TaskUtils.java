package com.todolist.backend.utils;

import java.time.LocalDate;

public class TaskUtils {

    public static Status calculateStatus(LocalDate deadline) {
        LocalDate currentDate = LocalDate.now();

        if (currentDate.isBefore(deadline)) {
            return Status.PENDENTE;
        } else {
            return Status.ATRASADA;
        }
    }
}
