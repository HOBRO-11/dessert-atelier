package com.yangnjo.dessert_atelier.common.dto.user;

import com.yangnjo.dessert_atelier.db.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserJoinDto {

    private String email;

    private String password;

    private String name;

    private String nickname;

    private Integer phone;

    public Users toEntity(){
        return Users.createUser(email, password, name, phone);
    }
}
