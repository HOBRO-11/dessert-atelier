package com.yangnjo.dessert_atelier.member.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.member.domain.entity.Member;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberOrigin;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberRole;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberStatus;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    
    boolean existsByEmail(String email);

    boolean existsByMemberRoleAndStatus(MemberRole memberRole, MemberStatus memberStatus);

    Optional<Member> findByEmailAndMemberOrigin(String email, MemberOrigin origin);

    

}
