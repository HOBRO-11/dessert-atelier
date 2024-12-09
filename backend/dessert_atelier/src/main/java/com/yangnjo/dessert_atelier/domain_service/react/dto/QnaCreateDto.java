package com.yangnjo.dessert_atelier.domain_service.react.dto;

import com.yangnjo.dessert_atelier.common.TextEscapeUtil;
import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.react.QnA;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QnaCreateDto {
    Long dpId;
    String password;
    Long memberId;
    String comment;

    public QnA toMemberQnAEntity(DisplayProduct dp, Member member) {
        String sanitizedComment = TextEscapeUtil.escapeUserInput(comment);
        return QnA.createMemberQnA(dp, member, sanitizedComment);
    }

    public QnA toGuestQnAEntity(DisplayProduct dp) {
        String sanitizedComment = TextEscapeUtil.escapeUserInput(comment);
        return QnA.createGuestQnA(dp, password, sanitizedComment);
    }
}
