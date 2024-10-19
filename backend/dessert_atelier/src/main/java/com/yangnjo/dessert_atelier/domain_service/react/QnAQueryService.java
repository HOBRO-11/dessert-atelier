package com.yangnjo.dessert_atelier.domain_service.react;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.react.QnAStatus;
import com.yangnjo.dessert_atelier.repository.dto.QnADto;

public interface QnAQueryService {

  Page<QnADto> getQnAsByDpIdAndStatus(Long dpId, QnAStatus status, PageOption pageOption);

}