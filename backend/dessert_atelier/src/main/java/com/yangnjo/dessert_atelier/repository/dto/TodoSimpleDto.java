package com.yangnjo.dessert_atelier.repository.dto;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain.TodoStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoSimpleDto {
    
    private Long id;

    private String orderCode;

    private TodoStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;
}
