package com.yangnjo.dessert_atelier.db.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.yangnjo.dessert_atelier.db.model.OrderStatus;

import jakarta.persistence.Column;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Addresses addresses;

    @JoinColumn(name = "delivery_code")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Deliveries deliveries;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @JdbcTypeCode(SqlTypes.JSON)
    @OneToOne(fetch = FetchType.LAZY)
    private OrderCarts orderCarts;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Orders createOrder(String code, Users users, String password, Addresses addresses,
            OrderCarts orderCarts) {
        Orders orders = new Orders();
        orders.code = code;
        if (users != null) {
            orders.users = users;
            users.addOrder(orders);
        }
        if (users == null) {
            orders.password = password;
        }
        orders.addresses = addresses;
        orders.orderCarts = orderCarts;
        orders.status = OrderStatus.PAYMENT_COMPLETED;
        return orders;
    }


    public void changeAddress(Addresses addresses) {
        this.addresses = addresses;
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

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    protected void setDelivery(Deliveries deliveries) {
        this.deliveries = deliveries;
    }
}
