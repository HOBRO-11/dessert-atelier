package com.yangnjo.dessert_atelier.common.page_util;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PageResponse {

    public static <T> ResponseEntity<Page<T>> resp(Page<T> page) {
        if (page == null || page.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    public static <T> PageImpl<T> of(List<T> content, PageOption pageOption, Supplier<Long> totalCounter) {
        int size = content.size();

        if (size < pageOption.getSize()) {
            return PageResponse.ofSizeLtPageOptionSize(content, pageOption);
        }

        Long totalCount = totalCounter.get();

        return PageResponse.ofSizeEqPageOptionSize(content, pageOption, totalCount);
    }

    private static <T> PageImpl<T> ofSizeEqPageOptionSize(List<T> content, PageOption pageOption, long total) {
        Pageable pageable = PageRequest.of(pageOption.getPage(), pageOption.getSize());
        return new PageImpl<>(content, pageable, total);
    }

    private static <T> PageImpl<T> ofSizeLtPageOptionSize(List<T> content, PageOption pageOption) {
        int size = content.size();
        if (size == 0) {
            return new PageImpl<>(Collections.emptyList());
        }
        Pageable pageable = PageRequest.of(pageOption.getPage(), pageOption.getSize());
        return new PageImpl<>(content, pageable, content.size());
    }

}
