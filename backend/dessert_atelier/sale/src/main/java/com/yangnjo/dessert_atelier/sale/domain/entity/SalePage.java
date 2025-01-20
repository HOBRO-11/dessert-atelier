package com.yangnjo.dessert_atelier.sale.domain.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.commons.model.BaseEntity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalePage extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false)
    @Type(JsonType.class)
    private List<String> subtitles;

    @Setter
    @Column(nullable = false)
    @Type(JsonType.class)
    private List<String> thumbnail;

    @Setter
    @Column(nullable = false)
    @Type(JsonType.class)
    private List<String> content;

    @Setter
    private Long productReactId;

    @Type(JsonType.class)
    private List<Long> optionHeaderIds;

    @OneToMany(mappedBy = "salePage")
    private List<SaleOptionHeader> productOptionHeaders = new ArrayList<>();

    @Builder
    public SalePage(String title, List<String> subtitles, List<String> thumbnail, List<String> content,
            List<Long> optionHeaderIds, Long productReactId) {
        this.title = title;
        this.subtitles = subtitles;
        this.thumbnail = thumbnail;
        this.content = content;
        this.optionHeaderIds = optionHeaderIds;
        this.productReactId = productReactId;
    }

    public void addOptionHeaderId(Long optionHeaderId) {
        if(this.optionHeaderIds == null) {
            this.optionHeaderIds = new ArrayList<>();
        }
        this.optionHeaderIds.add(optionHeaderId);
    }

    public void setOptionHeaderId(Integer index, Long optionHeaderId) {
        if(this.optionHeaderIds == null) {
            this.optionHeaderIds = new ArrayList<>();
        }

        if(this.optionHeaderIds.size() <= index) {
            for(int i = 0; i <= index; i++) {
                this.optionHeaderIds.add(null);
            }
        }

        this.optionHeaderIds.set(index, optionHeaderId);
    }

    protected void addOptionHeader(SaleOptionHeader optionHeader) {
        this.productOptionHeaders.add(optionHeader);
    }
}
