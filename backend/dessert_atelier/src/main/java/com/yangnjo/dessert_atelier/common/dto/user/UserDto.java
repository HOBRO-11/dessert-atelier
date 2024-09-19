package com.yangnjo.dessert_atelier.common.dto.user;

import com.yangnjo.dessert_atelier.db.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private final Long id;

    private final String email;

    private final String name;

    public static UserDto toDto(Users user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }
}
