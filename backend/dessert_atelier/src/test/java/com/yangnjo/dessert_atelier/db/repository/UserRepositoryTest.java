package com.yangnjo.dessert_atelier.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.dto.user.UserDto;
import com.yangnjo.dessert_atelier.db.entity.Users;
import com.yangnjo.dessert_atelier.db.model.UserStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@Rollback
public class UserRepositoryTest {

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
    void findTest() {

        // given
        int i = 0;

        String email = "test" + i + "@example.com";
        String password = "password" + i;
        String name = "testUser" + i;
        Integer phone = 123456789 + i;

        // when
        Users findUser = userRepository.findByEmail(email).get();

        // then
        testAssert(email, password, name, phone, findUser);
    }

    @Test
    void modifyTest() {

        // given
        String email = "test0@example.com";
        String newPassword = "newPassword";
        String newName = "John Doe";
        Integer newPhone = 123456789;

        // when
        Users findUser = userRepository.findByEmail(email).get();
        userRepository.modify(findUser, newName, newPassword, newPhone);

        em.flush();
        em.clear();

        // then
        Users assertUsers = userRepository.findByEmail(email).get();

        testAssert(email, newPassword, newName, newPhone, assertUsers);
    }

    @Test
    void searchTest() {

        // given
        int page = 1;
        int size = 2;

        // when
        List<UserDto> activeSearch = userRepository.search(page, size, UserStatus.ACTIVE);
        Long activeCount = userRepository.count(UserStatus.ACTIVE);

        List<UserDto> banSearch = userRepository.search(page, size, UserStatus.BAN);
        Long banCount = userRepository.count(UserStatus.BAN);

        em.flush();
        em.clear();

        // then
        assertEquals(activeSearch.size(), 2);
        assertEquals(activeCount, 10L);
        assertEquals(banSearch.size(), 0);
        assertEquals(banCount, 0);
    }

    private void testAssert(String email, String newPassword, String newName, Integer newPhone,
            Users assertUsers) {

        assertEquals(email, assertUsers.getEmail());
        assertEquals(newPassword, assertUsers.getPassword());
        assertEquals(newName, assertUsers.getName());
        assertEquals(newPhone, assertUsers.getPhone());
    }

}
