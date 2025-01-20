
package com.yangnjo.dessert_atelier.orders.domain.repostiory.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.commons.util.page.PeriodOption;
import com.yangnjo.dessert_atelier.orders.domain.entity.OrderStatus;
import com.yangnjo.dessert_atelier.orders.domain.entity.QOrderDetail;
import com.yangnjo.dessert_atelier.orders.domain.repostiory.query.OrderQueryService;
import com.yangnjo.dessert_atelier.orders.dto.OrderDetailDto;
import com.yangnjo.dessert_atelier.orders.dto.OrderSimpleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderDetailQueryServiceV1 implements OrderQueryService {

    private final JPAQueryFactory queryFactory;
    QOrderDetail orderDetail = QOrderDetail.orderDetail;
    
    @Override
    public Optional<OrderDetailDto> findByOrderId(Long orderId) {
        return Optional.ofNullable(queryFactory
                .select(OrderDetailDto.asDto())
                .from(orderDetail)
                .where(equalorderId(orderId))
                .fetchOne());
    }

    @Override
    public List<OrderSimpleDto> findAllSimpleByOrderStatus(OrderStatus orderStatus, PageOption pageOption,
            PeriodOption periodOption) {
        return queryFactory.select(OrderSimpleDto.asIncompleteDto())
                .from(orderDetail)
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(orderDetail.id))
                .fetch();
    }

    @Override
    public List<OrderSimpleDto> findAllSimpleByMemberId(Long memberId, PageOption pageOption,
            PeriodOption periodOption) {
        return queryFactory.select(OrderSimpleDto.asIncompleteDto())
                .from(orderDetail)
                .where(equalMemberId(memberId), PeriodOption.betweenLDT(orderDetail.createdAt, periodOption))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(orderDetail.id))
                .fetch();
    }

    @Override
    public Long countAllSimpleByOrderStatus(OrderStatus orderStatus) {
        return queryFactory.select(orderDetail.count())
                .from(orderDetail)
                .fetchOne();
    }

    @Override
    public Long countAllSimpleByMemberId(Long memberId) {
        return queryFactory.select(orderDetail.count())
                .from(orderDetail)
                .where(equalMemberId(memberId))
                .fetchOne();
    }

    private BooleanExpression equalorderId(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("orderId cat not be null");
        }
        return orderDetail.id.eq(orderId);
    }

    private BooleanExpression equalMemberId(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId cat not be null");
        }
        return orderDetail.memberId.eq(memberId);
    }

}
