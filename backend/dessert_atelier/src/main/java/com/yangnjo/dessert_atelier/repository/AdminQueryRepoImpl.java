package com.yangnjo.dessert_atelier.repository;

import static com.yangnjo.dessert_atelier.domain.admin.QStoreAdmin.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.AdminDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdminQueryRepoImpl {
  
  private final JPAQueryFactory queryFactory;

  public List<AdminDto> findAll(){
    return queryFactory.select(AdminDto.asDto()).from(storeAdmin).fetch();
  }

}
