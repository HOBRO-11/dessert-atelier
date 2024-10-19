package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.display_product.QDisplayProductPreset.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.dto.DppDto;
import com.yangnjo.dessert_atelier.repository.dto.DppSimpleDto;
import com.yangnjo.dessert_atelier.repository.query.DppQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DppQueryRepoImpl implements DppQueryRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<DppDto> find(Long dppId) {
        return Optional.ofNullable(
                queryFactory.select(DppDto.asDto())
                        .from(displayProductPreset)
                        .where(equalDppId(dppId))
                        .fetchOne());
    }

    @Override
    public List<DppSimpleDto> findSimpleByDpId(Long dpId, PageOption pageOption, PeriodOption periodOption) {
        return queryFactory.select(DppSimpleDto.asDto())
                .from(displayProductPreset)
                .where(equalDpId(dpId), betweenPeriod(periodOption))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(displayProductPreset.id))
                .fetch();
    }

    @Override
    public Long countSimpleByDpId(Long dpId, PeriodOption periodOption) {
        return queryFactory.select(displayProductPreset.count())
                .from(displayProductPreset)
                .where(equalDpId(dpId), betweenPeriod(periodOption))
                .fetchOne();
    }

    @Override
    public List<DppSimpleDto> findSimpleLikeNaming(String naming, PageOption pageOption, PeriodOption periodOption) {
        return queryFactory.select(DppSimpleDto.asDto())
                .from(displayProductPreset)
                .where(likeNaming(naming), betweenPeriod(periodOption))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(displayProductPreset.id))
                .fetch();
    }

    @Override
    public Long countSimpleLikeNaming(String naming, PeriodOption periodOption) {
        return queryFactory.select(displayProductPreset.count())
                .from(displayProductPreset)
                .where(likeNaming(naming), betweenPeriod(periodOption))
                .fetchOne();
    }

    @Override
    public List<DppSimpleDto> findSimpleByDpIdAndLikeNaming(Long dpId, String naming, PageOption pageOption,
            PeriodOption periodOption) {
        return queryFactory.select(DppSimpleDto.asDto())
                .from(displayProductPreset)
                .where(equalDpId(dpId), likeNaming(naming))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(displayProductPreset.id))
                .fetch();
    }

    @Override
    public Long countSimpleByDpIdAndLikeNaming(Long dpId, String naming, PeriodOption periodOption) {
        return queryFactory.select(displayProductPreset.count())
                .from(displayProductPreset)
                .where(equalDpId(dpId), likeNaming(naming), betweenPeriod(periodOption))
                .fetchOne();
    }

    @Override
    public List<DppSimpleDto> findSimpleByDpIdAndLikeTitle(Long dpId, String title, PageOption pageOption,
            PeriodOption periodOption) {
        return queryFactory.select(DppSimpleDto.asDto())
                .from(displayProductPreset)
                .where(equalDpId(dpId), likeTitle(title), betweenPeriod(periodOption))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(displayProductPreset.id))
                .fetch();
    }

    @Override
    public Long countSimpleByDpIdAndLikeTitle(Long dpId, String title, PeriodOption periodOption) {
        return queryFactory.select(displayProductPreset.count())
                .from(displayProductPreset)
                .where(equalDpId(dpId), likeTitle(title), betweenPeriod(periodOption))
                .fetchOne();
    }

    @Override
    public List<DppSimpleDto> findByDpIdsAndDefault(List<Long> dpIds, PageOption pageOption) {
        return queryFactory.select(DppSimpleDto.asDto())
                .from(displayProductPreset)
                .where(inDpIds(dpIds), isDefault())
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(displayProductPreset.id))
                .fetch();
    }

    @Override
    public Long countByDpIdsAndDefault(List<Long> dpIds) {
        return queryFactory.select(displayProductPreset.count())
                .from(displayProductPreset)
                .where(inDpIds(dpIds), isDefault())
                .fetchOne();
    }

    private BooleanExpression equalDppId(Long dppId) {
        return dppId != null ? displayProductPreset.id.eq(dppId) : null;
    }

    private BooleanExpression equalDpId(Long dpId) {
        return dpId != null ? displayProductPreset.displayProduct.id.eq(dpId) : null;
    }

    private BooleanExpression inDpIds(List<Long> dpIds) {
        return dpIds != null && !dpIds.isEmpty() ? displayProductPreset.displayProduct.id.in(dpIds) : null;
    }

    private BooleanExpression likeNaming(String naming) {
        return naming != null ? displayProductPreset.naming.like(naming) : null;
    }

    private Predicate likeTitle(String title) {
        return title != null ? displayProductPreset.title.like(title) : null;
    }

    private Predicate betweenPeriod(PeriodOption periodOption) {
        return displayProductPreset.startDateTime.between(periodOption.getStart(), periodOption.getEnd());
    }

    private BooleanExpression isDefault() {
        return displayProductPreset.isDefault.eq(true);
    }
}
