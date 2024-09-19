package com.yangnjo.dessert_atelier.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.yangnjo.dessert_atelier.common.dto.user.UserDto;
import com.yangnjo.dessert_atelier.db.entity.Users;
import com.yangnjo.dessert_atelier.db.model.UserStatus;
import com.yangnjo.dessert_atelier.db.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void setUp() {

        userRepository.deleteAll();

        List<Users> userList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String email = "test" + i + "@example.com";
            String password = "password" + i;
            String name = "testUser" + i;
            Integer phone = 123456789 + i;

            Users user = Users.createUser(email, password, name, phone);
            userList.add(user);
        }

        userRepository.saveAll(userList);

        em.flush();
        em.clear();
    }

    @Test
    public void count_test() {

        // when
        Long count = userRepository.count(UserStatus.ACTIVE);

        // then
        assertEquals(count, 10L);
    }

    @Test
    public void delete_test() {

        // given
        String email = "test" + 3 + "@example.com";

        // when
        Users deletedUsers = userRepository.findByEmail(email).get();
        userRepository.delete(deletedUsers);

        em.flush();
        em.clear();

        // then
        Optional<Users> findUserOptional = userRepository.findByEmail(email);
        assertThat(findUserOptional.isPresent()).isFalse();
    }

    @Test
    public void findUsers() {

        // given
        int page = 1;
        int size = 2;
        UserStatus userStatus = UserStatus.ACTIVE;

        // when
        Long count = userRepository.count(userStatus);
        Long totalPage = count / size;

        List<UserDto> userList = userRepository.search(page, size, userStatus);

        em.flush();
        em.clear();

        // then
        String email = "test" + 2 + "@example.com";

        assertEquals(totalPage, 5L);
        assertEquals(userList.get(0).getEmail(), email);
    }

    @Test
    public void join() {

        // given
        String email = "example@example.com";
        String password = "SecureP@ssw0rd";
        String name = "John Doe";
        Integer phone = 1234567890;

        // when
        Users user = Users.createUser(email, password, name, phone);
        userRepository.save(user);

        em.flush();
        em.clear();

        // then
        Users findUsers = userRepository.findByEmail(email).get();

        assertEquals(findUsers.getEmail(), email);
        assertEquals(findUsers.getPassword(), password);
        assertEquals(findUsers.getName(), name);
        assertEquals(findUsers.getPhone(), phone);
        assertEquals(findUsers.getUserStatus(), UserStatus.ACTIVE);
    }

    @Test
    public void modify() {

        // given
        String email = "test0@example.com";
        String newName = "John Doe";
        String newPassword = "newPassword";
        Integer newPhone = 123456789;

        // when
        Users findUser = userRepository.findByEmail(email).get();
        userRepository.modify(findUser, newName, newPassword, newPhone);

        em.flush();
        em.clear();

        // then
        Users assertUsers = userRepository.findByEmail(email).get();

        assertEquals(email, assertUsers.getEmail());
        assertEquals(newPassword, assertUsers.getPassword());
        assertEquals(newName, assertUsers.getName());
        assertEquals(newPhone, assertUsers.getPhone());
    }
}
