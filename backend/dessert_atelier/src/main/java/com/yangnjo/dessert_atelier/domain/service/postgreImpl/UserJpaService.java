package com.yangnjo.dessert_atelier.domain.service.postgreImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.common.dto.user.UserDto;
import com.yangnjo.dessert_atelier.common.dto.user.UserJoinDto;
import com.yangnjo.dessert_atelier.common.dto.user.UserModifyDto;
import com.yangnjo.dessert_atelier.common.dto.user.UserSearchDto;
import com.yangnjo.dessert_atelier.db.entity.Users;
import com.yangnjo.dessert_atelier.db.model.UserStatus;
import com.yangnjo.dessert_atelier.db.repository.UserRepository;
import com.yangnjo.dessert_atelier.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserJpaService implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto join(UserJoinDto dto) {

        boolean existsByEmail = userRepository.existsByEmail(dto.getEmail());

        if (existsByEmail) {
            // TODO
            throw new RuntimeException();
        }

        Users user = dto.toEntity();
        userRepository.save(user);

        return UserDto.toDto(user);
    }

    @Override
    public Page<UserDto> findUsers(UserSearchDto dto) {

        int page = dto.getPage();
        int size = dto.getSize();
        UserStatus userStatus = dto.getUserStatus();

        List<UserDto> contents = userRepository.search(page, size, userStatus);
        Long count = userRepository.count(userStatus);

        if (count == 0) {
            return Page.empty();
        }

        Pageable Pageable = PageRequest.of(page, size);

        return new PageImpl<>(contents, Pageable, size);
    }

    @Override
    public Long count(UserStatus userStatus) {
        return userRepository.count(userStatus);
    }

    @Override
    public boolean modify(UserModifyDto dto) {

        Users user = dto.getUser();
        String name = dto.getName();
        String password = dto.getPassword();
        Integer phone = dto.getPhone();

        boolean isModify = userRepository.modify(user, name, password, phone);

        if (isModify == false) {
            // TODO
            throw new RuntimeException();
        }

        return isModify;
    }

    @Override
    public boolean delete(Users user) {
        userRepository.delete(user);
        return true;
    }

}
