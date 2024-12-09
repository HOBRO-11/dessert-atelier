package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.order.QOrders.*;

import java.time.LocalDateTime;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.order.OrderStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class OrderSimpleDto {

    private Long orderCode;
    private Long memberId;
    private OrderStatus orderStatus;
    private Long totalPrice;
    private LocalDateTime createdAt;
    @Setter
    private String dppTitle;
    private Integer optionQuantityCount;

    /*
     * 반드시 setDppTitle 을 사용하여 DTO 완성을 마무리 짓자.
     * 
     * @see dppTitle
     */
    public OrderSimpleDto(Long orderCode, Long memberId, OrderStatus orderStatus, Long totalPrice,
            LocalDateTime createdAt, Integer optionQuantityCount) {
        this.orderCode = orderCode;
        this.memberId = memberId;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.optionQuantityCount = optionQuantityCount;
    }

    public static Expression<OrderSimpleDto> asIncompleteDto() {
        return Projections.constructor(OrderSimpleDto.class,
                orders.orderCode,
                orders.member.id,
                orders.orderStatus,
                orders.totalPrice,
                orders.createdAt,
                orders.optionQuantities.size());
    }

}
