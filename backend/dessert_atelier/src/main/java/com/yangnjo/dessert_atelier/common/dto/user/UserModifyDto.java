package com.yangnjo.dessert_atelier.common.dto.user;

import com.yangnjo.dessert_atelier.db.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserModifyDto {

    Users user;

    String name;

    String password;

    Integer phone;
}
