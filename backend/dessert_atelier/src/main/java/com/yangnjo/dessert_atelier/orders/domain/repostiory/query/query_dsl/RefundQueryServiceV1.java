package com.yangnjo.dessert_atelier.orders.domain.repostiory.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.orders.domain.entity.QRefund;
import com.yangnjo.dessert_atelier.orders.domain.entity.RefundStatus;
import com.yangnjo.dessert_atelier.orders.domain.repostiory.query.RefundQueryService;
import com.yangnjo.dessert_atelier.orders.dto.RefundDto;
import com.yangnjo.dessert_atelier.orders.dto.RefundSimpleDto;
import com.yangnjo.dessert_atelier.sale.domain.entity.QSaleOption;
import com.yangnjo.dessert_atelier.sale.domain.entity.QSalePage;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefundQueryServiceV1 implements RefundQueryService {

    private final JPAQueryFactory queryFactory;
    QRefund refund = QRefund.refund;
    QSaleOption saleOption = QSaleOption.saleOption;
    QSalePage salePage = QSalePage.salePage;

    @Override
    public List<RefundDto> findAllByOrderId(Long orderId) {
        return queryFactory
                .select(RefundDto.asDto())
                .from(refund)
                .where(equalOrderId(orderId))
                .fetch();
    }

    @Override
    public List<RefundSimpleDto> findAllByStatus(RefundStatus status, PageOption pageOption) {

        return queryFactory
                .select(RefundSimpleDto.asDto())
                .from(refund)
                .where(equalRefundStatus(status))
                .limit(pageOption.getSize())
                .offset(pageOption.getOffset())
                .orderBy(pageOption.getDirection(refund.id))
                .fetch();
    }

    @Override
    public Long countAllByStatus(RefundStatus status) {
        return queryFactory
                .select(refund.count())
                .from(refund)
                .where(equalRefundStatus(status))
                .fetchOne();
    }

    @Override
    public Optional<RefundDto> findById(Long refundId) {
        return Optional.ofNullable(queryFactory
                .select(RefundDto.asDto())
                .from(refund)
                .where(equalId(refundId))
                .fetchOne());
    }

    private BooleanExpression equalOrderId(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("orderId를 입력해주세요.");
        }
        return refund.orderDetail.id.eq(orderId);
    }

    private BooleanExpression equalId(Long refundId) {
        if (refundId == null) {
            throw new IllegalArgumentException("Refund id cannot be null");
        }
        return refund.id.eq(refundId);
    }

    private BooleanExpression equalRefundStatus(RefundStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Refund status cannot be null");
        }
        return refund.status.eq(status);
    }
}
