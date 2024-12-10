package com.yangnjo.dessert_atelier.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain_model.member.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    boolean existsByMemberIdAndNaming(Long memberId, String naming);

    Long countByMemberId(Long memberId);

}
