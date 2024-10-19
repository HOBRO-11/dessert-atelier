package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.admin.QStoreAdmin.*;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.AdminDto;
import com.yangnjo.dessert_atelier.repository.query.StoreAdminQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StoreAdminQueryRepoImpl implements StoreAdminQueryRepo {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<AdminDto> findAll() {
    return queryFactory.select(AdminDto.asDto()).from(storeAdmin).fetch();
  }

  @Override
  public Optional<AdminDto> find(Long adminId) {
    return Optional
        .ofNullable(queryFactory.select(AdminDto.asDto()).from(storeAdmin).where(storeAdmin.id.eq(adminId)).fetchOne());
  }

}
