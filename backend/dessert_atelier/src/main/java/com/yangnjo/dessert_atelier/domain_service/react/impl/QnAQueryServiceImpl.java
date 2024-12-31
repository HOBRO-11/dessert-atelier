package com.yangnjo.dessert_atelier.domain_service.react.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_model.react.QnAStatus;
import com.yangnjo.dessert_atelier.domain_service.react.QnAQueryService;
import com.yangnjo.dessert_atelier.repository.react.dto.QnADto;
import com.yangnjo.dessert_atelier.repository.react.query.QnAQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnAQueryServiceImpl implements QnAQueryService {

    private final QnAQueryRepo qnaQueryRepo;

    @Override
    public Page<QnADto> getAllByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption) {
        List<QnADto> dtos = qnaQueryRepo.findAllByMemberId(memberId, pageOption, periodOption);
        return PageResponse.of(dtos, pageOption, () -> qnaQueryRepo.countAllByMemberId(memberId));
    }

    @Override
    public Page<QnADto> getAllByDpIdAndStatus(Long dpId, QnAStatus status, PageOption pageOption) {
        List<QnADto> dtos = qnaQueryRepo.findAllByDpIdAndStatus(dpId, status, pageOption);
        return PageResponse.of(dtos, pageOption, () -> qnaQueryRepo.countAllByDpIdAndStatus(dpId, status));
    }

    @Override
    public Page<QnADto> getAllByDpIdAndExceptStatus(Long dpId, QnAStatus status, PageOption pageOption) {
        List<QnADto> dtos = qnaQueryRepo.findAllByDpIdAndExceptStatus(dpId, status, pageOption);
        return PageResponse.of(dtos, pageOption, () -> qnaQueryRepo.countAllByDpIdAndExceptStatus(dpId, status));
    }

}
