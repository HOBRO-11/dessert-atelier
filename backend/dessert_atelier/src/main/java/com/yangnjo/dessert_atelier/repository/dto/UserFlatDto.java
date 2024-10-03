package com.yangnjo.dessert_atelier.repository.dto;

import com.yangnjo.dessert_atelier.domain.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserFlatDto {

    private Long id;

    private String email;

    private String name;

    private String phone;

    private UserStatus status;
}
