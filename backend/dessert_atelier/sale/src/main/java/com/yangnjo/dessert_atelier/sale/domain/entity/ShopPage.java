package com.yangnjo.dessert_atelier.sale.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.commons.model.BaseEntity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(name = "UQ_SHOP_PAGE_NAMING", columnNames = {"naming"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopPage extends BaseEntity {

    @Setter
    private String naming;

    @Setter
    @Type(JsonType.class)
    private List<Long> salePageIds;

    private boolean isDefault = false;

    private LocalDateTime startedDateAt;

    private LocalDateTime endedDateAt;

    @Setter
    private boolean isDeleteAtEndOfEvent;

    @Builder
    public ShopPage(String naming, List<Long> salePageIds, boolean isDefault, LocalDateTime startedDateAt,
            LocalDateTime endedDateAt, boolean isDeleteAtEndOfEvent) {
        this.naming = naming;
        this.salePageIds = salePageIds;
        this.isDefault = isDefault;
        this.startedDateAt = startedDateAt;
        this.endedDateAt = endedDateAt;
        this.isDeleteAtEndOfEvent = isDeleteAtEndOfEvent;
    }

    public void setDateAt(LocalDateTime startedDateAt, LocalDateTime endedDateAt) {
        this.startedDateAt = startedDateAt;
        this.endedDateAt = endedDateAt;
    }

    public void setDefaultToTrue() {
        this.isDefault = true;
        this.isDeleteAtEndOfEvent = false;
    }

    public void setDefaultToFalse() {
        this.isDefault = false;
    }


}
