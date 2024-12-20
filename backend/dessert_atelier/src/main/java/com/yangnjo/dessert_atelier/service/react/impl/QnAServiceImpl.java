package com.yangnjo.dessert_atelier.service.react.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_model.react.QnAStatus;
import com.yangnjo.dessert_atelier.domain_service.react.QnACommandService;
import com.yangnjo.dessert_atelier.domain_service.react.QnAQueryService;
import com.yangnjo.dessert_atelier.repository.react.dto.QnADto;
import com.yangnjo.dessert_atelier.service.react.QnAService;
import com.yangnjo.dessert_atelier.service.react.dto.QnaCreateForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnAServiceImpl implements QnAService {

    private final QnACommandService qnaCommandService;
    private final QnAQueryService qnaQueryService;

    @Override
    public Page<QnADto> getAllByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption) {
        return qnaQueryService.getAllByMemberId(memberId, pageOption, periodOption);
    }

    @Override
    public Page<QnADto> getAllByDpIdAndStatus(Long dpId, QnAStatus status, PageOption pageOption) {
        return qnaQueryService.getAllByDpIdAndStatus(dpId, status, pageOption);
    }

    @Override
    public Page<QnADto> getAllByDpIdAndExceptStatus(Long dpId, QnAStatus status, PageOption pageOption) {
        return qnaQueryService.getAllByDpIdAndExceptStatus(dpId, status, pageOption);
    }

    @Override
    public Long createMemberQnA(QnaCreateForm form) {
        return qnaCommandService.createMemberQnA(form.toDto());
    }

    @Override
    public Long createGuestQnA(QnaCreateForm form) {
        return qnaCommandService.createGuestQnA(form.toDto());
    }

    @Override
    public void updateMemberQnAComment(Long qnaId, Long memberId, String newComment) {
        qnaCommandService.updateMemberQnAComment(qnaId, memberId, newComment);
    }

    @Override
    public void updateGuestQnAComment(Long qnaId, String password, String newComment) {
        qnaCommandService.updateGuestQnAComment(qnaId, password, newComment);
    }

    @Override
    public void answerQnA(Long qnaId, String answer) {
        qnaCommandService.answerQnA(qnaId, answer);
    }

    @Override
    public void removeAnswerQnA(Long qnaId) {
        qnaCommandService.removeAnswerQnA(qnaId);
    }

    @Override
    public void updateQnAStatus(Long qnaId, QnAStatus status) {
        qnaCommandService.updateQnAStatus(qnaId, status);
    }

    @Override
    public void deleteQnA(Long qnaId, Long memberId) {
        qnaCommandService.deleteQnA(qnaId, memberId);
    }

    @Override
    public void deleteGuestQnA(Long qnaId, String password) {
        qnaCommandService.deleteGuestQnA(qnaId, password);
    }

}
