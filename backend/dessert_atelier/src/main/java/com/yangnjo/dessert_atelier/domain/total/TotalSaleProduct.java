package com.yangnjo.dessert_atelier.domain.total;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain.product.Product;

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
public class TotalSaleProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "product_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  private Integer saleAmount;

  private LocalDate createdAt;

  public TotalSaleProduct(Product product, Integer saleAmount) {
    this.product = product;
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
