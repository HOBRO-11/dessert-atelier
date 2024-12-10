package com.yangnjo.dessert_atelier.domain_service.react.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
import com.yangnjo.dessert_atelier.domain_model.react.ReviewStatus;
import com.yangnjo.dessert_atelier.domain_service.react.ReviewQueryService;
import com.yangnjo.dessert_atelier.repository.react.dto.ReviewDto;
import com.yangnjo.dessert_atelier.repository.react.query.ReviewQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewQueryRepo reviewQueryRepo;

    @Override
    public Page<ReviewDto> getAllByDpIdAndExceptStatus(Long dpId, ReviewStatus status, PageOption pageOption) {
        List<ReviewDto> dtos = reviewQueryRepo.findAllByDppIdAndExceptStatus(dpId, status, pageOption);
        int size = dtos.size();
        if (size <= pageOption.getSize()) {
            return PageResponse.ofSizeLePageOptionSize(dtos, pageOption);
        }
        Long total = reviewQueryRepo.countAllByDpIdAndExceptStatus(dpId, status);
        return PageResponse.of(dtos, pageOption, total);
    }

    @Override
    public Page<ReviewDto> getAllByMemberId(Long memberId, PageOption pageOption) {
        List<ReviewDto> dtos = reviewQueryRepo.findAllByMemberId(memberId, pageOption);
        int size = dtos.size();
        if (size <= pageOption.getSize()) {
            return PageResponse.ofSizeLePageOptionSize(dtos, pageOption);
        }
        Long total = reviewQueryRepo.countAllByMemberId(memberId);
        return PageResponse.of(dtos, pageOption, total);
    }

    @Override
    public Page<ReviewDto> getAllByDpIdAndStatus(Long dpId, ReviewStatus status, PageOption pageOption) {
        List<ReviewDto> dtos = reviewQueryRepo.findAllByDppIdAndStatus(dpId, status, pageOption);
        int size = dtos.size();
        if (size <= pageOption.getSize()) {
            return PageResponse.ofSizeLePageOptionSize(dtos, pageOption);
        }
        Long total = reviewQueryRepo.countAllByDpIdAndStatus(dpId, status);
        return PageResponse.of(dtos, pageOption, total);
    }

}
