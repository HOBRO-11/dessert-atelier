package com.yangnjo.dessert_atelier.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain_model.member.Member;
import com.yangnjo.dessert_atelier.domain_model.member.MemberOrigin;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    
    boolean existsByEmail(String email);

    Optional<Member> findByEmailAndMemberOrigin(String email, MemberOrigin origin);

}
