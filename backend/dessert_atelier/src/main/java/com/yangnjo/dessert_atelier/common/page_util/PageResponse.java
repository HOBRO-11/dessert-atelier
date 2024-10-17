package com.yangnjo.dessert_atelier.common.page_util;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageResponse {

  public static <T> PageImpl<T> of(List<T> content, PageOption pageOption, long total) {
    Pageable pageable = PageRequest.of(pageOption.getPage(), pageOption.getSize());
    return new PageImpl<>(content, pageable, total);
  }

  public static <T> PageImpl<T> ofSizeLePageOptionSize(List<T> content, PageOption pageOption) {
    int size = content.size();
    if (size == 0) {
      return new PageImpl<>(Collections.emptyList());
    }
    Pageable pageable = PageRequest.of(pageOption.getPage(), pageOption.getSize());
    return new PageImpl<>(content, pageable, content.size());
  }

}
