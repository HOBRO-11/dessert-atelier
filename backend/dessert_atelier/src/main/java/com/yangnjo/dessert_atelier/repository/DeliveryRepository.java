package com.yangnjo.dessert_atelier.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.delivery.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

  Optional<Delivery> findByDeliveryCodeAndDeliveryCompanyId(String deliveryCode, Long deliveryCompanyId);

  Optional<Delivery> findByOrdersOrderCode(Long orderCode);

List<Delivery> findAllByOrdersOrderCodeIn(List<Long> orderCodes);
}
