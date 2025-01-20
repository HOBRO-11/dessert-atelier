package com.yangnjo.dessert_atelier.orders.domain.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.CartOptionProperty;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(indexes = {@Index(columnList = "member_id")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @Type(JsonType.class)
    private List<CartOptionProperty> properties;

    @Builder
    public CartOption(Long memberId, List<CartOptionProperty> properties) {
        this.memberId = memberId;
        this.properties = properties;
    }

    public void addProperty(CartOptionProperty property) {
        if (this.properties == null) {
            this.properties = new ArrayList<CartOptionProperty>();
        }
        else if (this.properties.contains(property)) {
            int idx = this.properties.indexOf(property);
            this.properties.get(idx).addQuantity(property.getQuantity());
            return;
        }
        this.properties.add(property);
    }

    public void removeProperty(Long dppId, List<Long> optionIds) {
        if (this.properties == null) {
            return;
        }
        this.properties.removeIf(property -> property.getOptionIds().equals(optionIds));
    }

    public void setIdToTest(Long id) {
        this.id = id;
    }
}
