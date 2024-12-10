package com.yangnjo.dessert_atelier.domain_service.react;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_model.react.QnAStatus;
import com.yangnjo.dessert_atelier.repository.react.dto.QnADto;

public interface QnAQueryService {

    Page<QnADto> getAllByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption);

    Page<QnADto> getAllByDpIdAndStatus(Long dpId, QnAStatus status, PageOption pageOption);

    Page<QnADto> getAllByDpIdAndExceptStatus(Long dpId, QnAStatus status, PageOption pageOption);

}