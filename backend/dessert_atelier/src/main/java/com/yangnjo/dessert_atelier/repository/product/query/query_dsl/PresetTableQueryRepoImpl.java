package com.yangnjo.dessert_atelier.repository.product.query.query_dsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain_model.product.QPresetTable;
import com.yangnjo.dessert_atelier.repository.product.dto.PresetTableDto;
import com.yangnjo.dessert_atelier.repository.product.query.PresetTableQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PresetTableQueryRepoImpl implements PresetTableQueryRepo {

    private final JPAQueryFactory queryFactory;
    QPresetTable presetTable = QPresetTable.presetTable;

    @Override
    public List<PresetTableDto> findAll() {
        return queryFactory.select(PresetTableDto.asDto())
                .from(presetTable)
                .orderBy(byNumbering())
                .fetch();
    }

    private OrderSpecifier<Integer> byNumbering() {
        return presetTable.numbering.asc();
    }
}
