package com.yangnjo.dessert_atelier.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.OneToMany;
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

    private Long totalPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.REMOVE)
    private List<OptionQuantity> optionQuantities = new ArrayList<>();

    public static Orders createUserOrder(String orderCode, Member member, Destination destination, Long totalPrice) {
        Orders orders = new Orders();
        orders.orderCode = orderCode;
        orders.member = member;
        member.addOrder(orders);
        orders.destination = destination;
        orders.totalPrice = totalPrice;
        orders.orderStatus = OrderStatus.PAYMENT_COMPLETED;
        return orders;
    }

    public static Orders createGuestOrder(String orderCode, String password, Destination destination, Long totalPrice) {
        Orders orders = new Orders();
        orders.orderCode = orderCode;
        orders.password = password;
        orders.destination = destination;
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

    protected void addOptionQuantity(OptionQuantity optionQuantity) {
        this.optionQuantities.add(optionQuantity);
        optionQuantity.setOrders(this);
    }

}
