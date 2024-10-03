package com.yangnjo.dessert_atelier.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.yangnjo.dessert_atelier.domain.vale_type.Destination;

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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @Column(name = "order_code", nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    private String password;

    @Embedded
    private Destination destination;

    @JoinColumn(name = "delivery_code")
    @OneToOne(fetch = FetchType.LAZY)
    private Deliveries deliveries;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderCarts orderCarts;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Orders createUserOrder(String code, Users users, Destination destination, OrderCarts orderCarts) {
        Orders orders = new Orders();
        orders.code = code;
        orders.users = users;
        users.addOrder(orders);
        orders.destination = destination;
        orders.orderCarts = orderCarts;
        orderCarts.setOrders(orders);
        orders.status = OrderStatus.PAYMENT_COMPLETED;
        return orders;
    }

    public static Orders createGuestOrder(String code, String password, Destination destination, OrderCarts orderCarts) {
        Orders orders = new Orders();
        orders.code = code;
        orders.password = password;
        orders.destination = destination;
        orders.orderCarts = orderCarts;
        orderCarts.setOrders(orders);
        orders.status = OrderStatus.PAYMENT_COMPLETED;
        return orders;
    }

    public void changeAddress(Destination destination) {
        this.destination = destination;
    }

    public void paymentCompleted() {
        this.status = OrderStatus.PAYMENT_COMPLETED;
    };

    public void requestForCancel() {
        this.status = OrderStatus.REQUEST_CANCEL;
    };

    public void cancel() {
        this.status = OrderStatus.CANCEL;
    };

    public void completed() {
        this.status = OrderStatus.COMPLETED;
    };

    public void refund() {
        this.status = OrderStatus.REFUND;
    };

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public void setDelivery(Deliveries deliveries) {
        this.deliveries = deliveries;
        deliveries.setOrders(this);
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

}
