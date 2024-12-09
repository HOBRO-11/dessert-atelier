package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.order.QOptionQuantity.*;
import static com.yangnjo.dessert_atelier.domain.order.QOrders.*;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.order.OrderStatus;
import com.yangnjo.dessert_atelier.domain.value_type.Destination;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class OrderDetailDto {

    private Long memberId;
    private Long orderCode;
    private OrderStatus orderStatus;
    private Long totalPrice;
    private Long deliveryFee;
    private Destination destination;
    private LocalDateTime createdAt;
    @Setter
    private List<OrderPropertyDto> orderPropertyDtos;

    /*
     * 반드시 setOrderPropertyDtos 을 사용하여 DTO 완성을 마무리 짓자.
     * 
     * @see orderPropertyDtos
     */
    public OrderDetailDto(Long memberId, Long orderCode, OrderStatus orderStatus, Long totalPrice,
            Long deliveryFee,
            Destination destination,
            LocalDateTime createdAt) {
        this.memberId = memberId;
        this.orderCode = orderCode;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.destination = destination;
        this.createdAt = createdAt;
    }

    public static Expression<OrderDetailDto> asIncompleteDto() {
        return Projections.constructor(OrderDetailDto.class,
                orders.member.id,
                orders.orderCode,
                orders.orderStatus,
                orders.totalPrice,
                orders.deliveryFee,
                orders.destination,
                orders.createdAt);
    }

    @Getter
    @ToString
    public static class OrderPropertyDto {
        private String title;
        private List<String> optionDescriptions;
        private Integer quantity;

        public OrderPropertyDto(String title, List<String> optionDescriptions, Integer quantity) {
            this.title = title;
            this.optionDescriptions = optionDescriptions;
            this.quantity = quantity;
        }

        public static Expression<OrderPropertyDto> asDto() {
            return Projections.constructor(OrderPropertyDto.class,
                    optionQuantity.displayProductPreset.title,
                    optionQuantity.optionIds,
                    optionQuantity.quantity);
        }

    }

}
