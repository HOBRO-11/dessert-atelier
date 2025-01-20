package com.yangnjo.dessert_atelier.react.dto;

import com.yangnjo.dessert_atelier.react.domain.entity.ProductQnAStatus;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductQna;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductReact;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductQnaCreateDto {
    Long memberId;
    Long productReactId;
    String password;
    String comment;

    public ProductQna toMemberQnAEntity(ProductReact productReact) {
        return ProductQna.builder().productReact(productReact).memberId(memberId).question(comment)
                .status(ProductQnAStatus.REQ).build();
    }

    public ProductQna toGuestQnAEntity(ProductReact productReact) {
        return ProductQna.builder().productReact(productReact).password(password).question(comment)
                .status(ProductQnAStatus.REQ).build();
    }
}
