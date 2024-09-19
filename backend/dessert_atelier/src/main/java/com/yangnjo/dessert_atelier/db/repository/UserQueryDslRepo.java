package com.yangnjo.dessert_atelier.db.repository;

import java.util.List;

import com.yangnjo.dessert_atelier.common.dto.user.UserDto;
import com.yangnjo.dessert_atelier.db.entity.Users;
import com.yangnjo.dessert_atelier.db.model.UserStatus;

public interface UserQueryDslRepo {

    boolean modify(Users user, String name, String password, Integer phone);

    List<UserDto> search(int page, int size, UserStatus userStatus);

    Long count(UserStatus userStatus);
}