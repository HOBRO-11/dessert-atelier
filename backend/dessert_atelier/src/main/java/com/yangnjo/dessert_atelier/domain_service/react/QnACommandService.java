package com.yangnjo.dessert_atelier.domain_service.react;

import com.yangnjo.dessert_atelier.domain.react.QnAStatus;
import com.yangnjo.dessert_atelier.domain_service.react.dto.QnaCreateDto;

public interface QnACommandService {

  Long createMemberQnA(QnaCreateDto dto);

  Long createGuestQnA(QnaCreateDto dto);

  void updateMemberQnAComment(Long qnaId, Long memberId, String newComment);

  void updateGuestQnAComment(Long qnaId, String password, String newComment);

  void answerQnA(Long qnaId, String answer);

  void removeAnswerQnA(Long qnaId);

  void updateQnAStatus(Long qnaId, QnAStatus status);

  void deleteQnA(Long qnaId, Long memberId);

  void deleteGuestQnA(Long qnaId, String password);

}