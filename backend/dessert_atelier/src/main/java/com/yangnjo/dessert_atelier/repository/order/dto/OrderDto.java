package com.yangnjo.dessert_atelier.repository.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.order.OrderStatus;
import com.yangnjo.dessert_atelier.domain_model.order.OrderedOption;
import com.yangnjo.dessert_atelier.domain_model.order.QOrders;
import com.yangnjo.dessert_atelier.domain_model.value_type.Destination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OrderDto {

    private Long orderCode;
    private Long memberId;
    private String guestPhone;
    private Destination destination;
    private OrderStatus orderStatus;
    private Long totalPrice;
    private Long deliveryFee;
    private LocalDateTime createdAt;
    private List<OrderedOption> orderedOptions;

    @Setter
    private List<OrderedOptionDto> orderedOptionDtos;

    /*
     * 반드시 setOrderPropertyDtos 을 사용하여 DTO 완성을 마무리 짓자.
     * 
     * @see orderPropertyDtos
     */
    public static Expression<OrderDto> asIncompleteDto() {
        QOrders orders = QOrders.orders;
        return Projections.constructor(OrderDto.class,
                orders.orderCode,
                orders.member.id,
                orders.guestPhone,
                orders.destination,
                orders.orderStatus,
                orders.totalPrice,
                orders.deliveryFee,
                orders.createdAt,
                orders.orderedOptions);
    }

    public OrderDto(Long orderCode, Long memberId, String guestPhone, Destination destination, OrderStatus orderStatus,
            Long totalPrice, Long deliveryFee, LocalDateTime createdAt, List<OrderedOption> orderedOptions) {
        this.orderCode = orderCode;
        this.memberId = memberId;
        this.guestPhone = guestPhone;
        this.destination = destination;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.createdAt = createdAt;
        this.orderedOptions = orderedOptions;
    }

    @Getter
    @AllArgsConstructor
    public static class OrderedOptionDto {
        private String displayProductTitle;
        private List<String> optionDescriptions;
        private Integer quantity;
    }
}
