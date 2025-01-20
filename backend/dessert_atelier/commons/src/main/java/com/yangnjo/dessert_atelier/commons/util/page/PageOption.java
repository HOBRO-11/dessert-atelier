package com.yangnjo.dessert_atelier.commons.util.page;

import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;

public class PageOption {

    /**
     * page
     */
    private int p;

    /**
     * size
     */
    private int s;

    private Direction d;

    public int getOffset() {
        return p * s;
    }

    public int getPage() {
        return p;
    }

    public int getSize() {
        return s;
    }

    public Direction getSort(){
        return d;
    }

    public PageOption(Integer p, Integer s, String d) {
        if (p == null || p < 1) {
            this.p = 0;
        } else {
            this.p = p - 1;
        }

        if (s == null || s < 1) {
            this.s = 10;
        } else if (s > 50) {
            this.s = 50;
        } else {
            this.s = s;
        }

        if (d != null && d.toLowerCase().equals("asc")) {
            this.d = Direction.ASC;
        } else {
            this.d = Direction.DESC;
        }
    }

    public <T extends Comparable<?>> OrderSpecifier<T> getDirection(Path<T> criteria) {
        if (d == Direction.DESC) {
            return new OrderSpecifier<T>(Order.DESC, criteria);
        }
        return new OrderSpecifier<T>(Order.ASC, criteria);
    }
}
