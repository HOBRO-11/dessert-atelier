package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.react.QnAStatus;
import com.yangnjo.dessert_atelier.repository.dto.QnADto;

public interface QnAQueryRepo {

  QnADto findById(Long id);

  List<QnADto> findAllByDpIdAndStatus(Long dpId, QnAStatus status, PageOption pageOption);

  List<QnADto> findAllByDpIdAndExceptStatus(Long dpId, QnAStatus status, PageOption pageOption);

  Long countByDpIdAndStatus(Long dpId, QnAStatus status);

  Long countByDpIdAndExceptStatus(Long dpId, QnAStatus status);

}