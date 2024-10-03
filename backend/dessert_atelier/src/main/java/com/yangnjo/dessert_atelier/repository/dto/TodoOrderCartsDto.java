package com.yangnjo.dessert_atelier.repository.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.yangnjo.dessert_atelier.domain.TodoStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoOrderCartsDto {

    private Long id;

    private String orderCode;

    private TodoStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    private List<Long> cartIds;

}
