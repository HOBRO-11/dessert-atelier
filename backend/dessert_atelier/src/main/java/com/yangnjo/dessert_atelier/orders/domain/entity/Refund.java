package com.yangnjo.dessert_atelier.orders.domain.entity;

import java.util.List;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.commons.model.BaseEntity;
import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.RefundOptionProperty;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refund extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id")
    private OrderDetail orderDetail;

    @Column(nullable = false)
    private List<String> reason;

    @Type(JsonType.class)
    public List<RefundOptionProperty> properties;

    @Column(nullable = false)
    private boolean isContainDeliveryFee;

    @Setter
    @Column(nullable = false)
    private RefundStatus status;

    @Builder
    public Refund(OrderDetail orderDetail, List<String> reason, List<RefundOptionProperty> properties,
            boolean isContainDeliveryFee, RefundStatus status) {
        this.orderDetail = orderDetail;
        this.reason = reason;
        this.properties = properties;
        this.isContainDeliveryFee = isContainDeliveryFee;
        this.status = status;
    }

}
