package com.yangnjo.dessert_atelier.repository.delivery;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain_model.delivery.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, String> {

    Optional<Delivery> findByOrdersOrderCode(Long orderCode);

    List<Delivery> findAllByOrdersOrderCodeIn(List<Long> orderCodes);
}
