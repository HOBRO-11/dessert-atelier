package com.yangnjo.dessert_atelier.repository.react.query;

import java.util.List;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_model.react.QnAStatus;
import com.yangnjo.dessert_atelier.repository.react.dto.QnADto;

public interface QnAQueryRepo {

    List<QnADto> findAllByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption);

    List<QnADto> findAllByDpIdAndStatus(Long dpId, QnAStatus status, PageOption pageOption);

    List<QnADto> findAllByDpIdAndExceptStatus(Long dpId, QnAStatus status, PageOption pageOption);

    Long countAllByMemberId(Long memberId);

    Long countAllByDpIdAndStatus(Long dpId, QnAStatus status);

    Long countAllByDpIdAndExceptStatus(Long dpId, QnAStatus status);

}