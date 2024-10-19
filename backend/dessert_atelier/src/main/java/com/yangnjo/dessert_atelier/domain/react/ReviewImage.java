package com.yangnjo.dessert_atelier.domain.react;

import java.util.Map;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Type(JsonType.class)
    // @Column(columnDefinition = "JSONB")
    private Map<String, String> imageUrls;

    public ReviewImage(Map<String, String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setIdToTest(Long id) {
        this.id = id;
    }

}
