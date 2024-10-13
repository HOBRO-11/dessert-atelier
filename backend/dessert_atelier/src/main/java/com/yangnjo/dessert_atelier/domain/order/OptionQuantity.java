package com.yangnjo.dessert_atelier.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain.display_product.Option;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_code")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "display_product_preset_id")
    private DisplayProductPreset displayProductPreset;

    @Type(JsonType.class)
    // @Column(columnDefinition = "JSONB")
    private List<Long> optionIds = new ArrayList<>();

    @Setter
    @Column(nullable = false)
    private Integer quantity;

    @Setter
    @Enumerated(value = EnumType.STRING)
    private OptionQuantityStatus status;

    private LocalDateTime updatedAt;

    public OptionQuantity(Orders orders, List<Option> options, DisplayProductPreset displayProductPreset, Integer quantity, OptionQuantityStatus status) {
        this.orders = orders;
        orders.addOptionQuantity(this);
        List<Long> optionIds = options.stream().map(Option::getId).collect(Collectors.toList());
        this.optionIds.addAll(optionIds);
        this.displayProductPreset = displayProductPreset;
        this.quantity = quantity;
        this.status = status;
        this.orders = orders;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
