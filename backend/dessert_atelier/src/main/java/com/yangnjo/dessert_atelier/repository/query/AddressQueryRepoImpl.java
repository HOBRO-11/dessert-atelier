package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.AddressDto;

import static com.yangnjo.dessert_atelier.domain.member.QAddress.address;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AddressQueryRepoImpl {

  private final JPAQueryFactory queryFactory;

  public List<AddressDto> findAllByMemberIdAndIsDefault(Long memberId, Boolean isDefault) {
    return queryFactory.select(AddressDto.asDto())
        .from(address)
        .where(equalMemberId(memberId), isDefault(isDefault))
        .fetch();
  }

  private BooleanExpression equalMemberId(Long memberId) {
    return memberId == null ? null : address.member.id.eq(memberId);
  }

  private BooleanExpression isDefault(Boolean isDefault) {
    return isDefault == null ? null : address.isDefault.eq(isDefault);
  }
}
