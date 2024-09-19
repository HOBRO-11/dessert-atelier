package com.yangnjo.dessert_atelier.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yangnjo.dessert_atelier.db.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long>, UserQueryDslRepo{

    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);
    
}
