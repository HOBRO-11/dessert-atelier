package com.yangnjo.dessert_atelier.common.page_util;

import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PageOption {

    @Getter
    private int page;

    @Getter
    private int size;

    public int getOffset() {
        return page * size;
    }

    private Direction direction;

    public <T extends Comparable<?>> OrderSpecifier<T> getDirection(Path<T> criteria) {
        if (direction == null) {
            return new OrderSpecifier<T>(Order.DESC, criteria);
        }
        return new OrderSpecifier<T>(Order.ASC, criteria);
    }
}
