package com.yangnjo.dessert_atelier.domain.order;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain.delivery.Delivery;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.value_type.Destination;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @Column(nullable = false, unique = true)
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String password;

    @Setter
    @Embedded
    private Destination destination;

    @Setter
    @JoinColumn(name = "delivery_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Delivery delivery;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @JoinColumn(name = "order_cart_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    private OrderCart orderCarts;

    private Long totalPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Orders createUserOrder(String orderCode, Member member, Destination destination, OrderCart orderCarts, Long totalPrice) {
        Orders orders = new Orders();
        orders.orderCode = orderCode;
        orders.member = member;
        member.addOrder(orders);
        orders.destination = destination;
        orders.orderCarts = orderCarts;
        orders.totalPrice = totalPrice;
        orders.orderStatus = OrderStatus.PAYMENT_COMPLETED;
        return orders;
    }

    public static Orders createGuestOrder(String orderCode, String password, Destination destination, OrderCart orderCarts, Long totalPrice) {
        Orders orders = new Orders();
        orders.orderCode = orderCode;
        orders.password = password;
        orders.destination = destination;
        orders.orderCarts = orderCarts;
        orders.totalPrice = totalPrice;
        orders.orderStatus = OrderStatus.PAYMENT_COMPLETED;
        return orders;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

}
