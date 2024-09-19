package com.yangnjo.dessert_atelier.common.dto.user;

import com.yangnjo.dessert_atelier.db.model.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSearchDto {

    int page;

    int size;

    UserStatus userStatus;
}
