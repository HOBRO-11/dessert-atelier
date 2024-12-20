package com.yangnjo.dessert_atelier.repository.total;

import java.time.LocalDate;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TotalSaleOptionRepository {

    private final EntityManager em;

    @Transactional(readOnly = false)
    public Integer upsert(LocalDate localDate, Long optionId, Integer saleAmount) {
        String nativeSqlString = "INSERT INTO total_sale_option (created_at, option_id, sale_amount) " +
                "VALUES (:date, :optionId, :saleAmount) " +
                "ON CONFLICT (created_at, option_id) " +
                "DO UPDATE SET sale_amount = total_sale_option.sale_amount + EXCLUDED.sale_amount";

        Query query = em.createNativeQuery(nativeSqlString, Integer.class);
        query.setParameter("date", localDate);
        query.setParameter("optionId", optionId);
        query.setParameter("saleAmount", saleAmount);

        return query.executeUpdate();
    }

}
