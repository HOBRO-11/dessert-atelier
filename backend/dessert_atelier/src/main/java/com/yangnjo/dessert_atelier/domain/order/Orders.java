package com.yangnjo.dessert_atelier.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Setter
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

    @OneToMany(mappedBy = "orders", cascade = CascadeType.REMOVE)
    private List<OptionQuantity> optionQuantities = new ArrayList<>();

    public static Orders createUserOrder(Long orderCode, Member member, Destination destination, Long totalPrice, Long deliveryFee) {
        Orders orders = new Orders();
        orders.orderCode = orderCode;
        orders.member = member;
        member.addOrder(orders);
        orders.destination = destination;
        orders.totalPrice = totalPrice;
        orders.deliveryFee = deliveryFee;
        orders.orderStatus = OrderStatus.PAYMENT_IN_PROGRESS;
        return orders;
    }

    public static Orders createGuestOrder(Long orderCode, String guestPhone, Destination destination, Long totalPrice, Long deliveryFee) {
        Orders orders = new Orders();
        orders.orderCode = orderCode;
        orders.guestPhone = guestPhone;
        orders.destination = destination;
        orders.totalPrice = totalPrice;
        orders.deliveryFee = deliveryFee;
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

    protected void addOptionQuantity(OptionQuantity optionQuantity) {
        this.optionQuantities.add(optionQuantity);
        optionQuantity.setOrders(this);
    }

    public void setCodeToTest(Long orderCode) {
        this.orderCode = orderCode;
    }

}
