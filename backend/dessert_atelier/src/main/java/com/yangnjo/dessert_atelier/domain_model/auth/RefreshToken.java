package com.yangnjo.dessert_atelier.domain_model.auth;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain_model.member.Member;

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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    private String refreshTokenSignature;

    private LocalDateTime expiredDate;

    public RefreshToken(Member member, String refreshTokenSignature, LocalDateTime expiredDate) {
        this.member = member;
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
