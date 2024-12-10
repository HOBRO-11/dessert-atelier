package com.yangnjo.dessert_atelier.domain_model.order;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.domain_model.member.Member;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = true)
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Type(JsonType.class)
    // @Column(columnDefinition = "JSONB")
    private List<BasketProperty> properties = new ArrayList<BasketProperty>();

    public Basket(Member member) {
        this.member = member;
    }

    public void addProperty(BasketProperty property) {
        this.properties.add(property);
    }

    public void removeProperty(Long dppId, List<Long> optionIds) {
        this.properties.removeIf(property -> property.getOptionIds().equals(optionIds));
    }

    public void setIdToTest(Long id) {
        this.id = id;
    }
}
