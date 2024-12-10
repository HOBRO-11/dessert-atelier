package com.yangnjo.dessert_atelier.domain_model.order;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.domain_model.member.Member;
import com.yangnjo.dessert_atelier.domain_model.value_type.Destination;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Long orderCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String guestPhone;

    @Embedded
    private Destination destination;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    private Long totalPrice;

    private Long deliveryFee;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Type(JsonType.class)
    private List<OrderedOption> orderedOptions;

    public static Orders createUserOrder(Long orderCode, Member member, Destination destination, Long totalPrice, Long deliveryFee, List<OrderedOption> orderedOptions) {
        Orders orders = new Orders();
        orders.orderCode = orderCode;
        orders.member = member;
        member.addOrder(orders);
        orders.destination = destination;
        orders.totalPrice = totalPrice;
        orders.deliveryFee = deliveryFee;
        orders.orderedOptions = orderedOptions;
        orders.orderStatus = OrderStatus.PAYMENT_IN_PROGRESS;
        return orders;
    }

    public static Orders createGuestOrder(Long orderCode, String guestPhone, Destination destination, Long totalPrice, Long deliveryFee, List<OrderedOption> orderedOptions) {
        Orders orders = new Orders();
        orders.orderCode = orderCode;
        orders.guestPhone = guestPhone;
        orders.destination = destination;
        orders.totalPrice = totalPrice;
        orders.deliveryFee = deliveryFee;
        orders.orderedOptions = orderedOptions;
        orders.orderStatus = OrderStatus.PAYMENT_IN_PROGRESS;
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

    public void setCodeToTest(Long orderCode) {
        this.orderCode = orderCode;
    }
}
