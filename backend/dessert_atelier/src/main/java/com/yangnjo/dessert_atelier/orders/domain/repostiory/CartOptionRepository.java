package com.yangnjo.dessert_atelier.orders.domain.repostiory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.orders.domain.entity.CartOption;

@Repository
public interface CartOptionRepository extends JpaRepository<CartOption, Long> {

    Optional<CartOption> findByMemberId(Long memberId);

    Long countByMemberId(Long memberId);

}
