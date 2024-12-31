package com.yangnjo.dessert_atelier.domain_service.react.impl;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_model.member.Member;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain_model.react.QnA;
import com.yangnjo.dessert_atelier.domain_model.react.QnAStatus;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.domain_service.react.QnACommandService;
import com.yangnjo.dessert_atelier.domain_service.react.dto.QnaCreateDto;
import com.yangnjo.dessert_atelier.domain_service.react.exception.QnANotFoundException;
import com.yangnjo.dessert_atelier.repository.member.MemberRepository;
import com.yangnjo.dessert_atelier.repository.product.DisplayProductRepository;
import com.yangnjo.dessert_atelier.repository.react.QnARepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class QnACommandServiceImpl implements QnACommandService {

    private final QnARepository qnaRepository;
    private final DisplayProductRepository displayProductRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long createMemberQnA(final QnaCreateDto dto) {
        DisplayProduct displayProduct = findDpById(dto.getDpId());
        Member member = findMemberById(dto.getMemberId());
        QnA qna = dto.toMemberQnAEntity(displayProduct, member);
        QnA savedQnA = qnaRepository.save(qna);
        return savedQnA.getId();
    }

    @Override
    public Long createGuestQnA(final QnaCreateDto dto) {
        DisplayProduct displayProduct = findDpById(dto.getDpId());
        QnA qna = dto.toGuestQnAEntity(displayProduct);
        QnA savedQnA = qnaRepository.save(qna);
        return savedQnA.getId();
    }

    @Override
    public void updateMemberQnAComment(Long qnaId, Long memberId, String newComment) {
        QnA qna = findQnaById(qnaId);

        checkAuthMember(memberId, qna);

        qna.changeComment(newComment);
    }

    @Override
    public void updateGuestQnAComment(Long qnaId, String password, String newComment) {
        QnA qna = findQnaById(qnaId);
        
        checkAuthGuest(qna, password);

        qna.changeComment(newComment);
    }

    @Override
    public void answerQnA(Long qnaId, String answer) {
        QnA qna = findQnaById(qnaId);
        qna.setAnswer(answer);
    }

    @Override
    public void removeAnswerQnA(Long qnaId) {
        QnA qna = findQnaById(qnaId);
        qna.setAnswer(null);
    }

    @Override
    public void updateQnAStatus(Long qnaId, QnAStatus status) {
        QnA qna = findQnaById(qnaId);
        qna.setQnaStatus(status);
    }

    @Override
    public void deleteQnA(Long qnaId, Long memberId) {
        QnA qna = findQnaById(qnaId);

        checkAuthMember(memberId, qna);

        qnaRepository.deleteById(qnaId);
    }

    @Override
    public void deleteGuestQnA(Long qnaId, String password) {
        QnA qna = findQnaById(qnaId);

        checkAuthGuest(qna, password);

        qnaRepository.deleteById(qnaId);
    }

    private QnA findQnaById(Long qnaId) {
        QnA qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new QnANotFoundException());
        return qna;
    }

    private DisplayProduct findDpById(Long dpId) {
        DisplayProduct displayProduct = displayProductRepository.findById(dpId)
                .orElseThrow(() -> new DisplayProductNotFountException());
        return displayProduct;
    }

    private Member findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
        return member;
    }

    private void checkAuthMember(Long memberId, QnA qna) {
        if (qna.getMember() == null) {
            throw new QnANotFoundException();
        }
        if (qna.getMember().getId() != memberId) {
            throw new AccessDeniedException("qna");
        }
    }

    private void checkAuthGuest(QnA qna, String password) {
        if (qna.getPassword() == null) {
            throw new QnANotFoundException();
        }
        if (qna.getPassword().equals(password) == false) {
            throw new QnANotFoundException();
        }
    }
}
