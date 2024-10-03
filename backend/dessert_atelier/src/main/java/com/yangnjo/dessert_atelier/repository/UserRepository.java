package com.yangnjo.dessert_atelier.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.Users;
import com.yangnjo.dessert_atelier.repository.query_dsl.UserQueryDslRepo;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>, UserQueryDslRepo {

    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);

}
