package com.yangnjo.dessert_atelier.domain_model.total;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain_model.product.Option;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TotalSaleOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "option_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Option option;

    private Integer saleAmount;

    private LocalDate createdAt;

    public TotalSaleOption(Option option, Integer saleAmount) {
        this.option = option;
        this.saleAmount = saleAmount;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now().toLocalDate();
    }

    public void setIdToTest(Long id) {
        this.id = id;
    }

}
