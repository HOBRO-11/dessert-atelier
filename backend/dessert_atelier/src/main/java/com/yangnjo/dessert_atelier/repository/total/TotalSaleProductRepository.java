package com.yangnjo.dessert_atelier.repository.total;

import java.time.LocalDate;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TotalSaleProductRepository {
  private final EntityManager em;

  @Transactional(readOnly = false)
  public Integer upsert(LocalDate localDate, Long productId, Integer saleAmount) {
    String nativeSqlString = "INSERT INTO total_sale_product (created_at, product_id, sale_amount) " +
        "VALUES (:date, :productId, :saleAmount) " +
        "ON CONFLICT (created_at, product_id) " +
        "DO UPDATE SET sale_amount = total_sale_product.sale_amount + EXCLUDED.sale_amount";

    Query query = em.createNativeQuery(nativeSqlString, Integer.class);
    query.setParameter("date", localDate);
    query.setParameter("productId", productId);
    query.setParameter("saleAmount", saleAmount);

    return query.executeUpdate();
  }
}
