package com.yangnjo.dessert_atelier.service.react;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_model.react.QnAStatus;
import com.yangnjo.dessert_atelier.repository.react.dto.QnADto;
import com.yangnjo.dessert_atelier.service.react.dto.QnaCreateForm;

public interface QnAService {

    Page<QnADto> getAllByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption);

    Page<QnADto> getAllByDpIdAndStatus(Long dpId, QnAStatus status, PageOption pageOption);

    Page<QnADto> getAllByDpIdAndExceptStatus(Long dpId, QnAStatus status, PageOption pageOption);

    Long createMemberQnA(QnaCreateForm form);

    Long createGuestQnA(QnaCreateForm form);

    void updateMemberQnAComment(Long qnaId, Long memberId, String newComment);

    void updateGuestQnAComment(Long qnaId, String password, String newComment);

    void answerQnA(Long qnaId, String answer);

    void removeAnswerQnA(Long qnaId);

    void updateQnAStatus(Long qnaId, QnAStatus status);

    void deleteQnA(Long qnaId, Long memberId);

    void deleteGuestQnA(Long qnaId, String password);
}
