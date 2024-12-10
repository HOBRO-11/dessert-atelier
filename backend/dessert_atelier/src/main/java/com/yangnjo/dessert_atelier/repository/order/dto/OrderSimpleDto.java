package com.yangnjo.dessert_atelier.repository.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.order.OrderStatus;
import com.yangnjo.dessert_atelier.domain_model.order.OrderedOption;
import com.yangnjo.dessert_atelier.domain_model.order.QOrders;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class OrderSimpleDto {

    private Long orderCode;
    private Long memberId;
    private String guestPhone;
    private OrderStatus orderStatus;
    private Long totalPrice;
    private LocalDateTime createdAt;
    private List<OrderedOption> orderedOptions;
    private Integer orderedOptionCount;
    @Setter
    private String dpTitle;

    /*
     * 반드시 setDppTitle 을 사용하여 DTO 완성을 마무리 짓자.
     * 
     * @see dppTitle
     */
    

    public static Expression<OrderSimpleDto> asIncompleteDto() {
        QOrders orders = QOrders.orders;
        return Projections.constructor(OrderSimpleDto.class,
                orders.orderCode,
                orders.member.id,
                orders.guestPhone,
                orders.orderStatus,
                orders.totalPrice,
                orders.createdAt,
                orders.orderedOptions,
                orders.orderedOptions.size());
    }

    public OrderSimpleDto(Long orderCode, Long memberId, String guestPhone, OrderStatus orderStatus, Long totalPrice,
            Long deliveryFee, LocalDateTime createdAt, List<OrderedOption> orderedOptions, Integer orderedOptionCount) {
        this.orderCode = orderCode;
        this.memberId = memberId;
        this.guestPhone = guestPhone;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.orderedOptions = orderedOptions;
        this.orderedOptionCount = orderedOptionCount == null ? 0 : orderedOptionCount;
    }

}
