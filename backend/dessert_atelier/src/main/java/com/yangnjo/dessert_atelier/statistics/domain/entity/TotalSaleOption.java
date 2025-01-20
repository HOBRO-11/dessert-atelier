package com.yangnjo.dessert_atelier.statistics.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(indexes = { @Index(columnList = "sale_option_id") })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TotalSaleOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long optionId;

    private Integer saleAmount;

    private LocalDate createdAt;

    @Builder
    public TotalSaleOption(Long optionId, Integer saleAmount) {
        this.optionId = optionId;
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
