package com.yangnjo.dessert_atelier.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.member.domain.entity.MemberAddress;

@Repository
public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {

    boolean existsByMemberIdAndNaming(Long memberId, String naming);

    Long countByMemberId(Long memberId);

}
