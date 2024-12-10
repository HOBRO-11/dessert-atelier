package com.yangnjo.dessert_atelier.repository.order;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain_model.member.Member;
import com.yangnjo.dessert_atelier.domain_model.order.Basket;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

    Optional<Basket> findByMember(Member users);

    Long countByMemberId(Long memberId);

    Optional<Basket> findByMemberId(Long id);

}
