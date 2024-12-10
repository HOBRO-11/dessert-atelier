package com.yangnjo.dessert_atelier.repository.delivery.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain_model.delivery.QDelivery;
import com.yangnjo.dessert_atelier.repository.delivery.dto.DeliveryDto;
import com.yangnjo.dessert_atelier.repository.delivery.query.DeliveryQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliveryQueryRepoImpl implements DeliveryQueryRepo {

    private final JPAQueryFactory queryFactory;
    QDelivery delivery = QDelivery.delivery;

    @Override
    public Optional<DeliveryDto> findById(String deliveryCode) {
        return Optional.ofNullable(
                queryFactory
                        .select(DeliveryDto.asDto())
                        .from(delivery)
                        .where(equalDeliveryCode(deliveryCode))
                        .fetchOne());
    }

    @Override
    public Optional<DeliveryDto> findByOrderCode(Long orderCode) {
        return Optional.ofNullable(
                queryFactory
                        .select(DeliveryDto.asDto())
                        .from(delivery)
                        .where(equalOrderCode(orderCode))
                        .fetchOne());
    }

    @Override
    public List<DeliveryDto> findAllByOrderCodeIn(List<Long> orderCodes) {
        return queryFactory
                .select(DeliveryDto.asDto())
                .from(delivery)
                .where(inOrderCode(orderCodes))
                .fetch();
    }

    private BooleanExpression equalOrderCode(Long orderCode) {
        return delivery.orders.orderCode.eq(orderCode);
    }

    private BooleanExpression equalDeliveryCode(String deliveryCode) {
        return delivery.deliveryCode.eq(deliveryCode);
    }

    private BooleanExpression inOrderCode(List<Long> orderCodes) {
        return delivery.orders.orderCode.in(orderCodes);
    }
}
