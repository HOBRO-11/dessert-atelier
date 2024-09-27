package com.yangnjo.dessert_atelier.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.db.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);
    
}
