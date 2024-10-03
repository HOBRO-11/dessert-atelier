package com.yangnjo.dessert_atelier.repository.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.yangnjo.dessert_atelier.domain.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderFlatDto {

    private String code;

    private Long userId;

    private String postCode;

    private String detailAddress;

    private String receiver;

    private int phone;

    private String deliveryCode;

    private OrderStatus status;

    private List<Long> cartIds;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
