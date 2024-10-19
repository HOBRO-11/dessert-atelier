package com.yangnjo.dessert_atelier.domain_service.react.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
import com.yangnjo.dessert_atelier.domain.react.QnAStatus;
import com.yangnjo.dessert_atelier.domain_service.react.QnAQueryService;
import com.yangnjo.dessert_atelier.repository.dto.QnADto;
import com.yangnjo.dessert_atelier.repository.query.QnAQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnAQueryServiceImpl implements QnAQueryService {

    private final QnAQueryRepo qnaQueryRepo;

    @Override
    public Page<QnADto> getQnAsByDpIdAndStatus(Long dpId, QnAStatus status, PageOption pageOption) {
        List<QnADto> dtos = qnaQueryRepo.findAllByDpIdAndStatus(dpId, status, pageOption);
        int size = dtos.size();
        if (size <= pageOption.getSize()) {
            return PageResponse.ofSizeLePageOptionSize(dtos, pageOption);
        }
        Long total = qnaQueryRepo.countByDpIdAndStatus(dpId, status);
        return PageResponse.of(dtos, pageOption, total);
    }

}
