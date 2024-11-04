package com.yangnjo.dessert_atelier.domain_service.react;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.member.MemberRole;
import com.yangnjo.dessert_atelier.domain.react.QnA;
import com.yangnjo.dessert_atelier.domain.react.QnAStatus;
import com.yangnjo.dessert_atelier.domain_service.react.dto.QnaCreateDto;
import com.yangnjo.dessert_atelier.domain_service.react.exception.QnANonAuthException;
import com.yangnjo.dessert_atelier.domain_service.react.exception.QnANotFoundException;
import com.yangnjo.dessert_atelier.domain_service.react.impl.QnACommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.DisplayProductRepository;
import com.yangnjo.dessert_atelier.repository.MemberRepository;
import com.yangnjo.dessert_atelier.repository.QnARepository;

class QnACommandServiceTest {

  @InjectMocks
  private QnACommandServiceImpl qnaCommandService;

  @Mock
  private QnARepository qnaRepository;

  @Mock
  private DisplayProductRepository displayProductRepository;

  @Mock
  private MemberRepository memberRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createMemberQnA_성공() {
    // Given
    Long dpId = 1L;
    Long qnaId = 1L;
    Long memberId = 1L;
    String comment = "테스트 질문";
    QnaCreateDto dto = new QnaCreateDto(dpId, null, memberId, comment);

    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);
    dp.setIdToTest(dpId);
    Member member = new Member("test@example.com", "password", "Test User", "1234567890", MemberRole.MEMBER, null);
    member.setIdToTest(memberId);
    QnA qna = QnA.createMemberQnA(dp, member, comment);
    qna.setIdToTest(qnaId);

    when(displayProductRepository.findById(dpId)).thenReturn(Optional.of(dp));
    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
    when(qnaRepository.save(any(QnA.class))).thenReturn(qna);

    // When
    Long result = qnaCommandService.createMemberQnA(dto);

    // Then
    assertNotNull(result);
    verify(displayProductRepository).findById(dpId);
    verify(memberRepository).findById(memberId);
    verify(qnaRepository).save(any(QnA.class));
  }

  @Test
  void createGuestQnA_성공() {
    // Given
    Long dpId = 1L;
    Long qnaId = 1L;
    String password = "1234";
    String comment = "테스트 질문";
    QnaCreateDto dto = new QnaCreateDto(dpId, password, null, comment);

    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);
    dp.setIdToTest(dpId);
    QnA qna = QnA.createGuestQnA(dp, password, comment);
    qna.setIdToTest(qnaId);

    when(displayProductRepository.findById(dpId)).thenReturn(Optional.of(dp));
    when(qnaRepository.save(any(QnA.class))).thenReturn(qna);

    // When
    Long result = qnaCommandService.createGuestQnA(dto);

    // Then
    assertNotNull(result);
    verify(displayProductRepository).findById(dpId);
    verify(qnaRepository).save(any(QnA.class));
  }

  @Test
  void updateMemberQnAComment_성공() {
    // Given
    Long qnaId = 1L;
    Long memberId = 1L;
    String newComment = "수정된 질문";
    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);

    Member member = new Member("test@example.com", "password", "Test User", "1234567890", MemberRole.MEMBER, null);
    member.setIdToTest(memberId);
    QnA qna = QnA.createMemberQnA(dp, member, "원래 질문");

    when(qnaRepository.findById(qnaId)).thenReturn(Optional.of(qna));

    // When
    qnaCommandService.updateMemberQnAComment(qnaId, memberId, newComment);

    // Then
    assertEquals(newComment, qna.getComment());
    verify(qnaRepository).findById(qnaId);
  }

  @Test
  void updateGuestQnAComment_성공() {
    // Given
    Long qnaId = 1L;
    String password = "1234";
    String newComment = "수정된 질문";
    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);

    QnA qna = QnA.createGuestQnA(dp, password, "원래 질문");

    when(qnaRepository.findById(qnaId)).thenReturn(Optional.of(qna));

    // When
    qnaCommandService.updateGuestQnAComment(qnaId, password, newComment);

    // Then
    assertEquals(newComment, qna.getComment());
    verify(qnaRepository).findById(qnaId);
  }

  @Test
  void answerQnA_성공() {
    // Given
    Long qnaId = 1L;
    String password = "1234";
    String question = "답변 내용";
    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);

    QnA qna = QnA.createGuestQnA(dp, password, question);

    when(qnaRepository.findById(qnaId)).thenReturn(Optional.of(qna));

    // When
    qnaCommandService.answerQnA(qnaId, question);

    // Then
    assertEquals(question, qna.getAnswer());
    verify(qnaRepository).findById(qnaId);
  }

  @Test
  void removeAnswerQnA_성공() {
    // Given
    Long qnaId = 1L;
    String password = "1234";
    String question = "답변 내용";
    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);

    QnA qna = QnA.createGuestQnA(dp, password, question);
    qna.setAnswer("기존 답변");

    when(qnaRepository.findById(qnaId)).thenReturn(Optional.of(qna));

    // When
    qnaCommandService.removeAnswerQnA(qnaId);

    // Then
    assertNull(qna.getAnswer());
    verify(qnaRepository).findById(qnaId);
  }

  @Test
  void updateQnAStatus_성공() {
    // Given
    Long qnaId = 1L;
    QnAStatus newStatus = QnAStatus.ANSWERED;
    String password = "1234";
    String question = "답변 내용";
    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);

    QnA qna = QnA.createGuestQnA(dp, password, question);

    when(qnaRepository.findById(qnaId)).thenReturn(Optional.of(qna));

    // When
    qnaCommandService.updateQnAStatus(qnaId, newStatus);

    // Then
    assertEquals(newStatus, qna.getQnaStatus());
    verify(qnaRepository).findById(qnaId);
  }

  @Test
  void deleteQnA_성공() {
    // Given
    Long qnaId = 1L;
    Long memberId = 1L;

    Member member = new Member("test@example.com", "password", "Test User", "1234567890", MemberRole.MEMBER, null);
    member.setIdToTest(memberId);
    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);

    QnA qna = QnA.createMemberQnA(dp, member, "질문");

    when(qnaRepository.findById(qnaId)).thenReturn(Optional.of(qna));

    // When
    qnaCommandService.deleteQnA(qnaId, memberId);

    // Then
    verify(qnaRepository).findById(qnaId);
    verify(qnaRepository).deleteById(qnaId);
  }

  @Test
  void deleteGuestQnA_성공() {
    // Given
    Long qnaId = 1L;
    String password = "1234";
    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);

    QnA qna = QnA.createGuestQnA(dp, password, "질문");

    when(qnaRepository.findById(qnaId)).thenReturn(Optional.of(qna));

    // When
    qnaCommandService.deleteGuestQnA(qnaId, password);

    // Then
    verify(qnaRepository).findById(qnaId);
    verify(qnaRepository).deleteById(qnaId);
  }

  @Test
  void updateMemberQnAComment_권한없음_실패() {
    // Given
    Long qnaId = 1L;
    Long memberId = 1L;
    Long wrongMemberId = 2L;
    String newComment = "수정된 질문";

    Member member = new Member("Test User", "1234567890", "Test User", "1234567890", MemberRole.MEMBER, null);
    member.setIdToTest(memberId);
    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);

    QnA qna = QnA.createMemberQnA(dp, member, "원래 질문");

    when(qnaRepository.findById(qnaId)).thenReturn(Optional.of(qna));

    // When & Then
    assertThrows(QnANonAuthException.class, () -> {
      qnaCommandService.updateMemberQnAComment(qnaId, wrongMemberId, newComment);
    });
  }

  @Test
  void updateGuestQnAComment_비밀번호불일치_실패() {
    // Given
    Long qnaId = 1L;
    String password = "1234";
    String wrongPassword = "4321";
    String newComment = "수정된 질문";

    DisplayProduct dp = new DisplayProduct("테스트 제품", "설명", "thumb.jpg", SaleStatus.ON_SALE);

    QnA qna = QnA.createGuestQnA(dp, password, "원래 질문");

    when(qnaRepository.findById(qnaId)).thenReturn(Optional.of(qna));

    // When & Then
    assertThrows(QnANotFoundException.class, () -> {
      qnaCommandService.updateGuestQnAComment(qnaId, wrongPassword, newComment);
    });
  }
}