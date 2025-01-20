package com.yangnjo.dessert_atelier.orders.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.commons.value_type.Address;
import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.OrderOptionProperty;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(indexes = {@Index(columnList = "member_id")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetail {

    @Id
    @Column(nullable = false, unique = true)
    private Long id;

    private Long memberId;

    private String guestEmail;

    private String guestPhone;

    @Setter
    private String deliveryCode;

    @Setter
    private String paymentId;

    @Setter
    @Embedded
    private Address address;

    @Type(JsonType.class)
    private List<OrderOptionProperty> properties;

    private Integer totalPrice;

    private Integer deliveryFee;

    @Setter
    private Integer checkSum;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "orderDetail")
    private List<Refund> refunds = new ArrayList<>();

    @Builder
    public OrderDetail(Long id, Long memberId, String guestEmail, String guestPhone, Address address,
            List<OrderOptionProperty> properties, Integer totalPrice, Integer deliveryFee, Integer checkSum,
            OrderStatus status) {
        this.id = id;
        this.memberId = memberId;
        this.guestEmail = guestEmail;
        this.guestPhone = guestPhone;
        this.address = address;
        this.properties = properties;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.checkSum = checkSum;
        this.status = status;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setIdToTest(Long id) {
        this.id = id;
    }

    public void addRefund(Refund refund) {
        if (this.refunds == null) {
            this.refunds = new ArrayList<>();
        }
        this.refunds.add(refund);
    }

}
