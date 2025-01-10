package com.todolist.backend.specification;

import com.todolist.backend.model.Task;
import com.todolist.backend.utils.Status;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TaskSpecifications {
    public static Specification<Task> hasDescription(String description) {
        return (root, query, criteriaBuilder) -> description == null || description.isEmpty()
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }

    public static Specification<Task> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> category == null || category.isEmpty()
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Task> hasStatus(Status status) {
        return (root, query, criteriaBuilder) -> status == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> hasDeadline(LocalDate deadline) {
        return (root, query, criteriaBuilder) -> deadline == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("deadline"), deadline);
    }
}
