package com.yangnjo.dessert_atelier.auth.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(indexes = { @Index(columnList = "member_id") })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String refreshTokenSignature;

    private LocalDateTime expiredDate;

    public RefreshToken(Long memberId, String refreshTokenSignature, LocalDateTime expiredDate) {
        this.memberId = memberId;
        this.refreshTokenSignature = refreshTokenSignature;
        this.expiredDate = expiredDate;
    }

    public RefreshToken updateToken(String refreshTokenSignature, LocalDateTime expiredDate) {
        if(refreshTokenSignature != null){
            this.refreshTokenSignature = refreshTokenSignature;
        }
        this.expiredDate = expiredDate;
        return this;
    }

}
